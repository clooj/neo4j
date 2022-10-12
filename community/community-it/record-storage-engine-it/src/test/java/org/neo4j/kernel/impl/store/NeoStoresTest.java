/*
 * Copyright (c) "Neo4j"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.impl.store;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_BYTE_ARRAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.neo4j.common.Subject.AUTH_DISABLED;
import static org.neo4j.configuration.GraphDatabaseInternalSettings.counts_store_rotation_timeout;
import static org.neo4j.configuration.GraphDatabaseSettings.DEFAULT_DATABASE_NAME;
import static org.neo4j.index.internal.gbptree.RecoveryCleanupWorkCollector.immediate;
import static org.neo4j.internal.recordstorage.StoreTokens.createReadOnlyTokenHolder;
import static org.neo4j.io.pagecache.context.CursorContext.NULL_CONTEXT;
import static org.neo4j.io.pagecache.context.EmptyVersionContextSupplier.EMPTY;
import static org.neo4j.kernel.impl.transaction.log.LogTailMetadata.EMPTY_LOG_TAIL;
import static org.neo4j.kernel.impl.transaction.log.entry.LogVersions.CURRENT_FORMAT_LOG_HEADER_SIZE;
import static org.neo4j.lock.LockService.NO_LOCK_SERVICE;
import static org.neo4j.lock.LockTracer.NONE;
import static org.neo4j.lock.ResourceLocker.IGNORE;
import static org.neo4j.memory.EmptyMemoryTracker.INSTANCE;
import static org.neo4j.storageengine.api.PropertySelection.ALL_PROPERTIES;
import static org.neo4j.storageengine.api.RelationshipSelection.ALL_RELATIONSHIPS;
import static org.neo4j.storageengine.api.TransactionApplicationMode.INTERNAL;
import static org.neo4j.storageengine.api.TransactionIdStore.BASE_TX_COMMIT_TIMESTAMP;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.function.LongSupplier;
import org.eclipse.collections.api.set.ImmutableSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.collection.Dependencies;
import org.neo4j.configuration.Config;
import org.neo4j.configuration.GraphDatabaseSettings;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.exceptions.KernelException;
import org.neo4j.exceptions.UnderlyingStorageException;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.index.internal.gbptree.RecoveryCleanupWorkCollector;
import org.neo4j.internal.id.DefaultIdGeneratorFactory;
import org.neo4j.internal.id.IdGeneratorFactory;
import org.neo4j.internal.id.IdSlotDistribution;
import org.neo4j.internal.id.IdType;
import org.neo4j.internal.id.indexed.IndexedIdGenerator;
import org.neo4j.internal.recordstorage.LockVerificationFactory;
import org.neo4j.internal.recordstorage.RecordIdType;
import org.neo4j.internal.recordstorage.RecordStorageEngine;
import org.neo4j.io.fs.FileSystemAbstraction;
import org.neo4j.io.fs.UncloseableDelegatingFileSystemAbstraction;
import org.neo4j.io.layout.recordstorage.RecordDatabaseLayout;
import org.neo4j.io.pagecache.PageCache;
import org.neo4j.io.pagecache.context.CursorContext;
import org.neo4j.io.pagecache.context.CursorContextFactory;
import org.neo4j.io.pagecache.tracing.PageCacheTracer;
import org.neo4j.kernel.api.txstate.TransactionState;
import org.neo4j.kernel.impl.api.DatabaseSchemaState;
import org.neo4j.kernel.impl.api.TransactionToApply;
import org.neo4j.kernel.impl.api.state.TxState;
import org.neo4j.kernel.impl.store.record.PropertyKeyTokenRecord;
import org.neo4j.kernel.impl.transaction.log.LogPosition;
import org.neo4j.kernel.impl.transaction.log.PhysicalTransactionRepresentation;
import org.neo4j.kernel.impl.transaction.log.files.LogFiles;
import org.neo4j.kernel.internal.GraphDatabaseAPI;
import org.neo4j.kernel.lifecycle.LifeSupport;
import org.neo4j.lock.LockTracer;
import org.neo4j.logging.NullLogProvider;
import org.neo4j.monitoring.Health;
import org.neo4j.storageengine.api.ClosedTransactionMetadata;
import org.neo4j.storageengine.api.CommandCreationContext;
import org.neo4j.storageengine.api.MetadataProvider;
import org.neo4j.storageengine.api.PropertyKeyValue;
import org.neo4j.storageengine.api.Reference;
import org.neo4j.storageengine.api.RelationshipVisitor;
import org.neo4j.storageengine.api.StandardConstraintRuleAccessor;
import org.neo4j.storageengine.api.StorageNodeCursor;
import org.neo4j.storageengine.api.StorageProperty;
import org.neo4j.storageengine.api.StoragePropertyCursor;
import org.neo4j.storageengine.api.StorageReader;
import org.neo4j.storageengine.api.StorageRelationshipScanCursor;
import org.neo4j.storageengine.api.StorageRelationshipTraversalCursor;
import org.neo4j.storageengine.api.TransactionId;
import org.neo4j.storageengine.api.TransactionIdStore;
import org.neo4j.storageengine.api.cursor.StoreCursors;
import org.neo4j.test.TestDatabaseManagementServiceBuilder;
import org.neo4j.test.extension.EphemeralNeo4jLayoutExtension;
import org.neo4j.test.extension.Inject;
import org.neo4j.test.extension.pagecache.EphemeralPageCacheExtension;
import org.neo4j.test.utils.TestDirectory;
import org.neo4j.token.DelegatingTokenHolder;
import org.neo4j.token.TokenHolders;
import org.neo4j.token.api.TokenHolder;
import org.neo4j.values.storable.Value;
import org.neo4j.values.storable.Values;

@EphemeralNeo4jLayoutExtension
@EphemeralPageCacheExtension
class NeoStoresTest {
    private static final NullLogProvider LOG_PROVIDER = NullLogProvider.getInstance();
    private static final CursorContextFactory CONTEXT_FACTORY = new CursorContextFactory(PageCacheTracer.NULL, EMPTY);

    @Inject
    private FileSystemAbstraction fs;

    @Inject
    private TestDirectory dir;

    @Inject
    private PageCache pageCache;

    @Inject
    private RecordDatabaseLayout databaseLayout;

    private TransactionState transactionState;
    private StorageReader storageReader;
    private TokenHolder propertyKeyTokenHolder;
    private RecordStorageEngine storageEngine;
    private LifeSupport life;

    @BeforeEach
    void setUpNeoStores() {
        Config config = Config.defaults();
        StoreFactory sf = getStoreFactory(config, databaseLayout, fs, NullLogProvider.getInstance());
        sf.openAllNeoStores(true).close();
        propertyKeyTokenHolder = new DelegatingTokenHolder(this::createPropertyKeyToken, TokenHolder.TYPE_PROPERTY_KEY);
    }

    @AfterEach
    void closeStorageEngine() {
        if (life != null) {
            life.shutdown();
            life = null;
        }
    }

    @Test
    void impossibleToGetStoreFromClosedNeoStoresContainer() {
        Config config = Config.defaults();
        StoreFactory sf = getStoreFactory(config, databaseLayout, fs, NullLogProvider.getInstance());
        NeoStores neoStores = sf.openAllNeoStores(true);

        assertNotNull(neoStores.getMetaDataStore());

        neoStores.close();

        var e = assertThrows(IllegalStateException.class, neoStores::getMetaDataStore);
        assertEquals("Specified store was already closed.", e.getMessage());
    }

    @Test
    void notAllowCreateDynamicStoreWithNegativeBlockSize() {
        Config config = Config.defaults();
        StoreFactory sf = getStoreFactory(config, databaseLayout, fs, NullLogProvider.getInstance());

        var e = assertThrows(IllegalArgumentException.class, () -> {
            try (NeoStores neoStores = sf.openNeoStores(true)) {
                neoStores.createDynamicArrayStore(
                        Path.of("someStore"), Path.of("someIdFile"), RecordIdType.ARRAY_BLOCK, -2);
            }
        });
        assertEquals("Block size of dynamic array store should be positive integer.", e.getMessage());
    }

    @Test
    void impossibleToGetNotRequestedStore() {
        Config config = Config.defaults();
        StoreFactory sf = getStoreFactory(config, databaseLayout, fs, NullLogProvider.getInstance());

        var e = assertThrows(IllegalStateException.class, () -> {
            try (NeoStores neoStores = sf.openNeoStores(true, StoreType.NODE_LABEL)) {
                neoStores.getMetaDataStore();
            }
        });
        assertEquals(
                "Specified store was not initialized. Please specify " + StoreType.META_DATA.name()
                        + " as one of the stores types that should be open to be able to use it.",
                e.getMessage());
    }

    @Test
    void testRels1() throws Exception {
        reinitializeStores(databaseLayout);
        startTx();
        int relType1 = (int) nextId(RelationshipType.class);
        String typeName = "relationshiptype1";
        transactionState.relationshipTypeDoCreateForName(typeName, false, relType1);
        long[] nodeIds = new long[3];
        for (int i = 0; i < 3; i++) {
            nodeIds[i] = nextId(Node.class);
            transactionState.nodeDoCreate(nodeIds[i]);
            nodeAddProperty(nodeIds[i], index("nisse"), 10 - i);
        }
        for (int i = 0; i < 2; i++) {
            transactionState.relationshipDoCreate(nextId(Relationship.class), relType1, nodeIds[i], nodeIds[i + 1]);
        }
        commitTx();
        startTx();
        for (int i = 0; i < 3; i += 2) {
            deleteRelationships(nodeIds[i]);
            transactionState.nodeDoDelete(nodeIds[i]);
        }
        commitTx();
    }

    @Test
    void testRels2() throws Exception {
        reinitializeStores(databaseLayout);
        startTx();
        int relType1 = (int) nextId(RelationshipType.class);
        String typeName = "relationshiptype1";
        transactionState.relationshipTypeDoCreateForName(typeName, false, relType1);
        long[] nodeIds = new long[3];
        for (int i = 0; i < 3; i++) {
            nodeIds[i] = nextId(Node.class);
            transactionState.nodeDoCreate(nodeIds[i]);
            nodeAddProperty(nodeIds[i], index("nisse"), 10 - i);
        }
        for (int i = 0; i < 2; i++) {
            transactionState.relationshipDoCreate(nextId(Relationship.class), relType1, nodeIds[i], nodeIds[i + 1]);
        }
        transactionState.relationshipDoCreate(nextId(Relationship.class), relType1, nodeIds[0], nodeIds[2]);
        commitTx();
        startTx();
        for (int i = 0; i < 3; i++) {
            deleteRelationships(nodeIds[i]);
            transactionState.nodeDoDelete(nodeIds[i]);
        }
        commitTx();
    }

    @Test
    void testRels3() throws Exception {
        // test linked list stuff during relationship delete
        reinitializeStores(databaseLayout);
        startTx();
        int relType1 = (int) nextId(RelationshipType.class);
        transactionState.relationshipTypeDoCreateForName("relationshiptype1", false, relType1);
        long[] nodeIds = new long[8];
        for (int i = 0; i < nodeIds.length; i++) {
            nodeIds[i] = nextId(Node.class);
            transactionState.nodeDoCreate(nodeIds[i]);
        }
        for (int i = 0; i < nodeIds.length / 2; i++) {
            transactionState.relationshipDoCreate(nextId(Relationship.class), relType1, nodeIds[i], nodeIds[i * 2]);
        }
        long rel5 = nextId(Relationship.class);
        transactionState.relationshipDoCreate(rel5, relType1, nodeIds[0], nodeIds[5]);
        long rel2 = nextId(Relationship.class);
        transactionState.relationshipDoCreate(rel2, relType1, nodeIds[1], nodeIds[2]);
        long rel3 = nextId(Relationship.class);
        transactionState.relationshipDoCreate(rel3, relType1, nodeIds[1], nodeIds[3]);
        long rel6 = nextId(Relationship.class);
        transactionState.relationshipDoCreate(rel6, relType1, nodeIds[1], nodeIds[6]);
        long rel1 = nextId(Relationship.class);
        transactionState.relationshipDoCreate(rel1, relType1, nodeIds[0], nodeIds[1]);
        long rel4 = nextId(Relationship.class);
        transactionState.relationshipDoCreate(rel4, relType1, nodeIds[0], nodeIds[4]);
        long rel7 = nextId(Relationship.class);
        transactionState.relationshipDoCreate(rel7, relType1, nodeIds[0], nodeIds[7]);
        commitTx();
        startTx();
        relDelete(rel7);
        relDelete(rel4);
        relDelete(rel1);
        relDelete(rel6);
        relDelete(rel3);
        relDelete(rel2);
        relDelete(rel5);
        commitTx();
    }

    @Test
    void logVersionUpdate() throws Exception {
        Path storeDir = dir.directory("logVersion");
        DatabaseManagementService managementService = startDatabase(storeDir);
        try {
            var database = (GraphDatabaseAPI) managementService.database(DEFAULT_DATABASE_NAME);
            var metadataProvider = database.getDependencyResolver().resolveDependency(MetadataProvider.class);
            assertEquals(0, metadataProvider.getCurrentLogVersion());
            LogFiles logFiles = database.getDependencyResolver().resolveDependency(LogFiles.class);
            for (int i = 0; i < 12; i++) {
                logFiles.getLogFile().rotate();
                assertEquals(i + 1, metadataProvider.getCurrentLogVersion());
            }
        } finally {
            managementService.shutdown();
        }

        managementService = startDatabase(storeDir);
        try {
            GraphDatabaseAPI db = (GraphDatabaseAPI) managementService.database(DEFAULT_DATABASE_NAME);
            var metadataProvider = db.getDependencyResolver().resolveDependency(MetadataProvider.class);
            assertEquals(12, metadataProvider.getCurrentLogVersion());
        } finally {
            managementService.shutdown();
        }
    }

    @Test
    void shouldInitializeTheTxIdToOne() {
        StoreFactory factory = getStoreFactory(Config.defaults(), databaseLayout, fs, LOG_PROVIDER);
        try (NeoStores neoStores = factory.openAllNeoStores(true)) {
            neoStores.getMetaDataStore();
        }

        try (NeoStores neoStores = factory.openAllNeoStores()) {
            long lastCommittedTransactionId = neoStores.getMetaDataStore().getLastCommittedTransactionId();
            assertEquals(TransactionIdStore.BASE_TX_ID, lastCommittedTransactionId);
        }
    }

    @Test
    void shouldThrowUnderlyingStorageExceptionWhenFailingToLoadStorage() throws IOException {
        FileSystemAbstraction fileSystem = fs;
        StoreFactory factory = getStoreFactory(Config.defaults(), databaseLayout, fileSystem, LOG_PROVIDER);

        try (NeoStores neoStores = factory.openAllNeoStores(true)) {
            neoStores.getMetaDataStore();
        }
        fileSystem.deleteFile(databaseLayout.metadataStore());

        assertThrows(StoreNotFoundException.class, () -> {
            try (NeoStores neoStores = factory.openAllNeoStores()) {
                neoStores.getMetaDataStore();
            }
        });
    }

    @Test
    void shouldSetHighestTransactionIdWhenNeeded() {
        // GIVEN
        StoreFactory factory = getStoreFactory(Config.defaults(), databaseLayout, fs, LOG_PROVIDER);

        try (NeoStores neoStore = factory.openAllNeoStores(true)) {
            MetaDataStore store = neoStore.getMetaDataStore();
            store.setLastCommittedAndClosedTransactionId(
                    40, 4444, BASE_TX_COMMIT_TIMESTAMP, CURRENT_FORMAT_LOG_HEADER_SIZE, 0);

            // WHEN
            store.transactionCommitted(42, 6666, BASE_TX_COMMIT_TIMESTAMP);

            // THEN
            assertEquals(new TransactionId(42, 6666, BASE_TX_COMMIT_TIMESTAMP), store.getLastCommittedTransaction());
            assertEquals(
                    new ClosedTransactionMetadata(
                            40, new LogPosition(0, CURRENT_FORMAT_LOG_HEADER_SIZE), 4444, BASE_TX_COMMIT_TIMESTAMP),
                    store.getLastClosedTransaction());
        }
    }

    @Test
    void shouldNotSetHighestTransactionIdWhenNeeded() {
        // GIVEN
        StoreFactory factory = getStoreFactory(Config.defaults(), databaseLayout, fs, LOG_PROVIDER);

        try (NeoStores neoStore = factory.openAllNeoStores(true)) {
            MetaDataStore store = neoStore.getMetaDataStore();
            store.setLastCommittedAndClosedTransactionId(
                    40, 4444, BASE_TX_COMMIT_TIMESTAMP, CURRENT_FORMAT_LOG_HEADER_SIZE, 0);

            // WHEN
            store.transactionCommitted(39, 3333, BASE_TX_COMMIT_TIMESTAMP);

            // THEN
            assertEquals(new TransactionId(40, 4444, BASE_TX_COMMIT_TIMESTAMP), store.getLastCommittedTransaction());
            assertEquals(
                    new ClosedTransactionMetadata(
                            40, new LogPosition(0, CURRENT_FORMAT_LOG_HEADER_SIZE), 4444, BASE_TX_COMMIT_TIMESTAMP),
                    store.getLastClosedTransaction());
        }
    }

    @Test
    void shouldCloseAllTheStoreEvenIfExceptionsAreThrown() {
        // given
        Config defaults = Config.defaults(counts_store_rotation_timeout, Duration.ofMinutes(60));
        String errorMessage = "Failing for the heck of it";
        StoreFactory factory = new StoreFactory(
                databaseLayout,
                defaults,
                new CloseFailingDefaultIdGeneratorFactory(fs, errorMessage),
                pageCache,
                PageCacheTracer.NULL,
                fs,
                NullLogProvider.getInstance(),
                CONTEXT_FACTORY,
                false,
                EMPTY_LOG_TAIL);
        NeoStores neoStore = factory.openAllNeoStores(true);

        var ex = assertThrows(UnderlyingStorageException.class, neoStore::close);
        assertEquals(errorMessage, ex.getCause().getMessage());
    }

    @Test
    void isPresentAfterCreatingAllStores() throws Exception {
        // given
        fs.deleteRecursively(databaseLayout.databaseDirectory());
        DefaultIdGeneratorFactory idFactory =
                new DefaultIdGeneratorFactory(fs, immediate(), PageCacheTracer.NULL, databaseLayout.getDatabaseName());
        StoreFactory factory = new StoreFactory(
                databaseLayout,
                Config.defaults(),
                idFactory,
                pageCache,
                PageCacheTracer.NULL,
                fs,
                LOG_PROVIDER,
                CONTEXT_FACTORY,
                false,
                EMPTY_LOG_TAIL);

        // when
        try (NeoStores ignore = factory.openAllNeoStores(true)) {
            // then
            assertTrue(NeoStores.isStorePresent(fs, databaseLayout));
        }
    }

    @Test
    void isPresentFalseAfterCreatingAllButLastStoreType() throws Exception {
        // given
        fs.deleteRecursively(databaseLayout.databaseDirectory());
        DefaultIdGeneratorFactory idFactory =
                new DefaultIdGeneratorFactory(fs, immediate(), PageCacheTracer.NULL, databaseLayout.getDatabaseName());
        StoreFactory factory = new StoreFactory(
                databaseLayout,
                Config.defaults(),
                idFactory,
                pageCache,
                PageCacheTracer.NULL,
                fs,
                LOG_PROVIDER,
                CONTEXT_FACTORY,
                false,
                EMPTY_LOG_TAIL);
        StoreType[] allStoreTypes = StoreType.values();
        StoreType[] allButLastStoreTypes = Arrays.copyOf(allStoreTypes, allStoreTypes.length - 1);

        // when
        try (NeoStores ignore = factory.openNeoStores(true, allButLastStoreTypes)) {
            // then
            assertFalse(NeoStores.isStorePresent(fs, databaseLayout));
        }
    }

    private void reinitializeStores(RecordDatabaseLayout databaseLayout) {
        Dependencies dependencies = new Dependencies();
        Config config = Config.defaults(GraphDatabaseSettings.fail_on_missing_files, false);
        dependencies.satisfyDependency(config);
        closeStorageEngine();
        IdGeneratorFactory idGeneratorFactory =
                new DefaultIdGeneratorFactory(fs, immediate(), PageCacheTracer.NULL, databaseLayout.getDatabaseName());

        TokenHolders tokenHolders = new TokenHolders(
                createReadOnlyTokenHolder(TokenHolder.TYPE_PROPERTY_KEY),
                createReadOnlyTokenHolder(TokenHolder.TYPE_LABEL),
                createReadOnlyTokenHolder(TokenHolder.TYPE_RELATIONSHIP_TYPE));
        storageEngine = new RecordStorageEngine(
                databaseLayout,
                config,
                pageCache,
                fs,
                NullLogProvider.getInstance(),
                NullLogProvider.getInstance(),
                tokenHolders,
                new DatabaseSchemaState(NullLogProvider.getInstance()),
                new StandardConstraintRuleAccessor(),
                i -> i,
                NO_LOCK_SERVICE,
                mock(Health.class),
                idGeneratorFactory,
                immediate(),
                INSTANCE,
                EMPTY_LOG_TAIL,
                LockVerificationFactory.NONE,
                CONTEXT_FACTORY,
                PageCacheTracer.NULL);
        life = new LifeSupport();
        life.add(storageEngine);
        life.add(storageEngine.schemaAndTokensLifecycle());
        life.start();

        NeoStores neoStores = storageEngine.testAccessNeoStores();
        storageReader = storageEngine.newReader();
    }

    private void startTx() {
        transactionState = new TxState();
    }

    private void commitTx() throws Exception {
        CursorContext cursorContext = NULL_CONTEXT;
        try (CommandCreationContext commandCreationContext = storageEngine.newCommandCreationContext();
                var storeCursors = storageEngine.createStorageCursors(NULL_CONTEXT)) {
            commandCreationContext.initialize(
                    cursorContext,
                    storeCursors,
                    CommandCreationContext.NO_OLD_SEQUENCE_NUMBER_SUPPLIER,
                    CommandCreationContext.NO_SEQUENCE_NUMBER,
                    IGNORE,
                    () -> LockTracer.NONE);
            var commands = storageEngine.createCommands(
                    transactionState,
                    storageReader,
                    commandCreationContext,
                    NONE,
                    tx -> tx,
                    cursorContext,
                    storeCursors,
                    INSTANCE);
            PhysicalTransactionRepresentation tx =
                    new PhysicalTransactionRepresentation(commands, EMPTY_BYTE_ARRAY, -1, -1, -1, -1, AUTH_DISABLED);
            storageEngine.apply(new TransactionToApply(tx, cursorContext, storeCursors), INTERNAL);
        }
    }

    private int index(String key) throws KernelException {
        return propertyKeyTokenHolder.getOrCreateId(key);
    }

    private long nextId(Class<?> clazz) {
        NeoStores neoStores = storageEngine.testAccessNeoStores();
        if (clazz.equals(PropertyKeyTokenRecord.class)) {
            return neoStores.getPropertyKeyTokenStore().nextId(NULL_CONTEXT);
        }
        if (clazz.equals(RelationshipType.class)) {
            return neoStores.getRelationshipTypeTokenStore().nextId(NULL_CONTEXT);
        }
        if (clazz.equals(Node.class)) {
            return neoStores.getNodeStore().nextId(NULL_CONTEXT);
        }
        if (clazz.equals(Relationship.class)) {
            return neoStores.getRelationshipStore().nextId(NULL_CONTEXT);
        }
        throw new IllegalArgumentException(clazz.getName());
    }

    private StorageRelationshipTraversalCursor allocateRelationshipTraversalCursor(StorageNodeCursor node) {
        StorageRelationshipTraversalCursor relationships =
                storageReader.allocateRelationshipTraversalCursor(NULL_CONTEXT, StoreCursors.NULL);
        node.relationships(relationships, ALL_RELATIONSHIPS);
        return relationships;
    }

    private StorageNodeCursor allocateNodeCursor(long nodeId) {
        StorageNodeCursor nodeCursor = storageReader.allocateNodeCursor(NULL_CONTEXT, StoreCursors.NULL);
        nodeCursor.single(nodeId);
        return nodeCursor;
    }

    private void deleteRelationships(long nodeId) {
        try (StorageNodeCursor nodeCursor = allocateNodeCursor(nodeId)) {
            assertTrue(nodeCursor.next());
            try (StorageRelationshipTraversalCursor relationships = allocateRelationshipTraversalCursor(nodeCursor)) {
                while (relationships.next()) {
                    relDelete(relationships.entityReference());
                }
            }
        }
    }

    private void relDelete(long id) {
        RelationshipVisitor<RuntimeException> visitor = (relId, type, startNode, endNode) ->
                transactionState.relationshipDoDelete(relId, type, startNode, endNode);
        if (!transactionState.relationshipVisit(id, visitor)) {
            try (StorageRelationshipScanCursor cursor =
                    storageReader.allocateRelationshipScanCursor(NULL_CONTEXT, StoreCursors.NULL)) {
                cursor.single(id);
                if (!cursor.next()) {
                    throw new RuntimeException("Relationship " + id + " not found");
                }
                visitor.visit(id, cursor.type(), cursor.sourceNodeReference(), cursor.targetNodeReference());
            }
        }
    }

    private DatabaseManagementService startDatabase(Path storeDir) {
        return new TestDatabaseManagementServiceBuilder(storeDir)
                .setFileSystem(new UncloseableDelegatingFileSystemAbstraction(fs))
                .build();
    }

    private int createPropertyKeyToken(String name, boolean internal) {
        return (int) nextId(PropertyKeyTokenRecord.class);
    }

    private StorageProperty nodeAddProperty(long nodeId, int key, Object value) {
        StorageProperty property = new PropertyKeyValue(key, Values.of(value));
        StorageProperty oldProperty = null;
        try (StorageNodeCursor nodeCursor = storageReader.allocateNodeCursor(NULL_CONTEXT, StoreCursors.NULL)) {
            nodeCursor.single(nodeId);
            if (nodeCursor.next()) {
                StorageProperty fetched = getProperty(key, nodeCursor.propertiesReference());
                if (fetched != null) {
                    oldProperty = fetched;
                }
            }
        }

        if (oldProperty == null) {
            transactionState.nodeDoAddProperty(nodeId, key, property.value());
        } else {
            transactionState.nodeDoChangeProperty(nodeId, key, property.value());
        }
        return property;
    }

    private StorageProperty getProperty(int key, Reference propertyReference) {
        try (StoragePropertyCursor propertyCursor =
                storageReader.allocatePropertyCursor(NULL_CONTEXT, StoreCursors.NULL, INSTANCE)) {
            propertyCursor.initNodeProperties(propertyReference, ALL_PROPERTIES);
            if (propertyCursor.next()) {
                Value oldValue = propertyCursor.propertyValue();
                if (oldValue != null) {
                    return new PropertyKeyValue(key, oldValue);
                }
            }
        }
        return null;
    }

    private StoreFactory getStoreFactory(
            Config config, RecordDatabaseLayout databaseLayout, FileSystemAbstraction fs, NullLogProvider logProvider) {
        return new StoreFactory(
                databaseLayout,
                config,
                new DefaultIdGeneratorFactory(fs, immediate(), PageCacheTracer.NULL, databaseLayout.getDatabaseName()),
                pageCache,
                PageCacheTracer.NULL,
                fs,
                logProvider,
                CONTEXT_FACTORY,
                false,
                EMPTY_LOG_TAIL);
    }

    private static class CloseFailingDefaultIdGeneratorFactory extends DefaultIdGeneratorFactory {
        private final String errorMessage;

        CloseFailingDefaultIdGeneratorFactory(FileSystemAbstraction fs, String errorMessage) {
            super(fs, immediate(), PageCacheTracer.NULL, DEFAULT_DATABASE_NAME);
            this.errorMessage = errorMessage;
        }

        @Override
        protected IndexedIdGenerator instantiate(
                FileSystemAbstraction fs,
                PageCache pageCache,
                RecoveryCleanupWorkCollector recoveryCleanupWorkCollector,
                Path fileName,
                LongSupplier highIdSupplier,
                long maxValue,
                IdType idType,
                boolean readOnly,
                Config config,
                CursorContextFactory contextFactory,
                String databaseName,
                ImmutableSet<OpenOption> openOptions,
                IdSlotDistribution slotDistribution) {
            if (RecordIdType.NODE.equals(idType)) {
                // Return a special id generator which will throw exception on close
                return new IndexedIdGenerator(
                        pageCache,
                        fs,
                        fileName,
                        immediate(),
                        idType,
                        allowLargeIdCaches,
                        () -> 6 * 7,
                        maxValue,
                        readOnly,
                        config,
                        databaseName,
                        contextFactory,
                        IndexedIdGenerator.NO_MONITOR,
                        openOptions,
                        slotDistribution,
                        PageCacheTracer.NULL) {
                    @Override
                    public synchronized void close() {
                        super.close();
                        throw new IllegalStateException(errorMessage);
                    }
                };
            }
            return super.instantiate(
                    fs,
                    pageCache,
                    recoveryCleanupWorkCollector,
                    fileName,
                    highIdSupplier,
                    maxValue,
                    idType,
                    readOnly,
                    config,
                    contextFactory,
                    databaseName,
                    openOptions,
                    slotDistribution);
        }
    }
}
