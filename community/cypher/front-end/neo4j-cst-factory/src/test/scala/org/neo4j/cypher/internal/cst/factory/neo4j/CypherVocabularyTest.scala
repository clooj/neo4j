/*
 * Copyright (c) "Neo4j"
 * Neo4j Sweden AB [https://neo4j.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.cypher.internal.cst.factory.neo4j

import org.neo4j.cypher.internal.cst.factory.neo4j.ast.CypherErrorVocabulary
import org.neo4j.cypher.internal.parser.CypherParser
import org.neo4j.cypher.internal.util.test_helpers.CypherFunSuite

class CypherVocabularyTest extends CypherFunSuite {

  test("user facing token names are human readable") {
    val vocab = new CypherErrorVocabulary
    Range.inclusive(1, vocab.getMaxTokenType).foreach { token =>
      val displayName = vocab.getDisplayName(token)
      val symbolicName = vocab.getSymbolicName(token)
      val literalName = vocab.getLiteralName(token)
      withClue(s"displayName=$displayName, symbolicName=$symbolicName, literalName=$literalName\n") {
        expectedDisplayNames.get(token) match {
          case Some(expected) => displayName shouldBe expected
          case _ => fail(
              s"""Hello! I can see that you have added a new token type ($symbolicName)
                 |You need to add an expected display name in ${getClass.getName}.
                 |The reason why we fail for every new token here is that these names
                 |are user facing and we want to make sure all have a readable name.
                 |""".stripMargin
            )
        }
      }
    }
  }

  private def expectedDisplayNames: Map[Int, String] = Map(
    CypherParser.SPACE -> "' '",
    CypherParser.SINGLE_LINE_COMMENT -> "'//'",
    CypherParser.DECIMAL_DOUBLE -> "a float value",
    CypherParser.UNSIGNED_DECIMAL_INTEGER -> "an integer value",
    CypherParser.UNSIGNED_HEX_INTEGER -> "a hexadecimal integer value",
    CypherParser.UNSIGNED_OCTAL_INTEGER -> "an octal integer value",
    CypherParser.ACCESS -> "'ACCESS'",
    CypherParser.ACTIVE -> "'ACTIVE'",
    CypherParser.ADMIN -> "'ADMIN'",
    CypherParser.ADMINISTRATOR -> "'ADMINISTRATOR'",
    CypherParser.ALIAS -> "'ALIAS'",
    CypherParser.ALIASES -> "'ALIASES'",
    CypherParser.ALL_SHORTEST_PATHS -> "'allShortestPaths'",
    CypherParser.ALL -> "'ALL'",
    CypherParser.ALTER -> "'ALTER'",
    CypherParser.AND -> "'AND'",
    CypherParser.ANY -> "'ANY'",
    CypherParser.ARRAY -> "'ARRAY'",
    CypherParser.AS -> "'AS'",
    CypherParser.ASC -> "'ASC'",
    CypherParser.ASSERT -> "'ASSERT'",
    CypherParser.ASSIGN -> "'ASSIGN'",
    CypherParser.AT -> "'AT'",
    CypherParser.BAR -> "'|'",
    CypherParser.BINDINGS -> "'BINDINGS'",
    CypherParser.BOOLEAN -> "'BOOLEAN'",
    CypherParser.BOOSTED -> "'BOOSTED'",
    CypherParser.BOTH -> "'BOTH'",
    CypherParser.BREAK -> "'BREAK'",
    CypherParser.BRIEF -> "'BRIEF'",
    CypherParser.BTREE -> "'BTREE'",
    CypherParser.BUILT -> "'BUILT'",
    CypherParser.BY -> "'BY'",
    CypherParser.CALL -> "'CALL'",
    CypherParser.CASE -> "'CASE'",
    CypherParser.CHANGE -> "'CHANGE'",
    CypherParser.CIDR -> "'CIDR'",
    CypherParser.COLLECT -> "'COLLECT'",
    CypherParser.COLON -> "':'",
    CypherParser.COLONCOLON -> "'::'",
    CypherParser.COMMA -> "','",
    CypherParser.COMMAND -> "'COMMAND'",
    CypherParser.COMMANDS -> "'COMMANDS'",
    CypherParser.COMMIT -> "'COMMIT'",
    CypherParser.COMPOSITE -> "'COMPOSITE'",
    CypherParser.CONCURRENT -> "'CONCURRENT'",
    CypherParser.CONSTRAINT -> "'CONSTRAINT'",
    CypherParser.CONSTRAINTS -> "'CONSTRAINTS'",
    CypherParser.CONTAINS -> "'CONTAINS'",
    CypherParser.COPY -> "'COPY'",
    CypherParser.CONTINUE -> "'CONTINUE'",
    CypherParser.COUNT -> "'COUNT'",
    CypherParser.CREATE -> "'CREATE'",
    CypherParser.CSV -> "'CSV'",
    CypherParser.CURRENT -> "'CURRENT'",
    CypherParser.DATA -> "'DATA'",
    CypherParser.DATABASE -> "'DATABASE'",
    CypherParser.DATABASES -> "'DATABASES'",
    CypherParser.DATE -> "'DATE'",
    CypherParser.DATETIME -> "'DATETIME'",
    CypherParser.DBMS -> "'DBMS'",
    CypherParser.DEALLOCATE -> "'DEALLOCATE'",
    CypherParser.DEFAULT -> "'DEFAULT'",
    CypherParser.DEFINED -> "'DEFINED'",
    CypherParser.DELETE -> "'DELETE'",
    CypherParser.DENY -> "'DENY'",
    CypherParser.DESC -> "'DESC'",
    CypherParser.DESTROY -> "'DESTROY'",
    CypherParser.DETACH -> "'DETACH'",
    CypherParser.DIFFERENT -> "'DIFFERENT'",
    CypherParser.DOLLAR -> "'$'",
    CypherParser.DISTINCT -> "'DISTINCT'",
    CypherParser.DIVIDE -> "'/'",
    CypherParser.DOT -> "'.'",
    CypherParser.DOTDOT -> "'..'",
    CypherParser.DOUBLEBAR -> "'||'",
    CypherParser.DRIVER -> "'DRIVER'",
    CypherParser.DROP -> "'DROP'",
    CypherParser.DRYRUN -> "'DRYRUN'",
    CypherParser.DUMP -> "'DUMP'",
    CypherParser.DURATION -> "'DURATION'",
    CypherParser.EACH -> "'EACH'",
    CypherParser.EDGE -> "'EDGE'",
    CypherParser.ENABLE -> "'ENABLE'",
    CypherParser.ELEMENT -> "'ELEMENT'",
    CypherParser.ELEMENTS -> "'ELEMENTS'",
    CypherParser.ELSE -> "'ELSE'",
    CypherParser.ENCRYPTED -> "'ENCRYPTED'",
    CypherParser.END -> "'END'",
    CypherParser.ENDS -> "'ENDS'",
    CypherParser.EQ -> "'='",
    CypherParser.ErrorChar -> "'ErrorChar'",
    CypherParser.EXECUTABLE -> "'EXECUTABLE'",
    CypherParser.EXECUTE -> "'EXECUTE'",
    CypherParser.EXIST -> "'EXIST'",
    CypherParser.EXISTENCE -> "'EXISTENCE'",
    CypherParser.EXISTS -> "'EXISTS'",
    CypherParser.ERROR -> "'ERROR'",
    CypherParser.FAIL -> "'FAIL'",
    CypherParser.FALSE -> "'FALSE'",
    CypherParser.FIELDTERMINATOR -> "'FIELDTERMINATOR'",
    CypherParser.FINISH -> "'FINISH'",
    CypherParser.FLOAT -> "'FLOAT'",
    CypherParser.FOR -> "'FOR'",
    CypherParser.FOREACH -> "'FOREACH'",
    CypherParser.FROM -> "'FROM'",
    CypherParser.FULLTEXT -> "'FULLTEXT'",
    CypherParser.FUNCTIONS -> "'FUNCTIONS'",
    CypherParser.GE -> "'>='",
    CypherParser.GRANT -> "'GRANT'",
    CypherParser.GRAPH -> "'GRAPH'",
    CypherParser.GRAPHS -> "'GRAPHS'",
    CypherParser.GROUP -> "'GROUP'",
    CypherParser.GT -> "'>'",
    CypherParser.HEADERS -> "'HEADERS'",
    CypherParser.HOME -> "'HOME'",
    CypherParser.IF -> "'IF'",
    CypherParser.IMPERSONATE -> "'IMPERSONATE'",
    CypherParser.IMMUTABLE -> "'IMMUTABLE'",
    CypherParser.IN -> "'IN'",
    CypherParser.INDEX -> "'INDEX'",
    CypherParser.INDEXES -> "'INDEXES'",
    CypherParser.INFINITY -> "'INFINITY'",
    CypherParser.INSERT -> "'INSERT'",
    CypherParser.INT -> "'INT'",
    CypherParser.INTEGER -> "'INTEGER'",
    CypherParser.IS -> "'IS'",
    CypherParser.JOIN -> "'JOIN'",
    CypherParser.KEY -> "'KEY'",
    CypherParser.LABEL -> "'LABEL'",
    CypherParser.LABELS -> "'LABELS'",
    CypherParser.AMPERSAND -> "'&'",
    CypherParser.EXCLAMATION_MARK -> "'!'",
    CypherParser.LBRACKET -> "'['",
    CypherParser.LCURLY -> "'{'",
    CypherParser.LE -> "'<='",
    CypherParser.LEADING -> "'LEADING'",
    CypherParser.LIMITROWS -> "'LIMITROWS'",
    CypherParser.LIST -> "'LIST'",
    CypherParser.LOAD -> "'LOAD'",
    CypherParser.LOCAL -> "'LOCAL'",
    CypherParser.LOOKUP -> "'LOOKUP'",
    CypherParser.LPAREN -> "'('",
    CypherParser.LT -> "'<'",
    CypherParser.MANAGEMENT -> "'MANAGEMENT'",
    CypherParser.MAP -> "'MAP'",
    CypherParser.MATCH -> "'MATCH'",
    CypherParser.MERGE -> "'MERGE'",
    CypherParser.MINUS -> "'-'",
    CypherParser.PERCENT -> "'%'",
    CypherParser.INVALID_NEQ -> "'!='",
    CypherParser.NEQ -> "'<>'",
    CypherParser.NAME -> "'NAME'",
    CypherParser.NAMES -> "'NAMES'",
    CypherParser.NAN -> "'NAN'",
    CypherParser.NFC -> "'NFC'",
    CypherParser.NFD -> "'NFD'",
    CypherParser.NFKC -> "'NFKC'",
    CypherParser.NFKD -> "'NFKD'",
    CypherParser.NEW -> "'NEW'",
    CypherParser.NODE -> "'NODE'",
    CypherParser.NODETACH -> "'NODETACH'",
    CypherParser.NODES -> "'NODES'",
    CypherParser.NONE -> "'NONE'",
    CypherParser.NORMALIZE -> "'NORMALIZE'",
    CypherParser.NORMALIZED -> "'NORMALIZED'",
    CypherParser.NOT -> "'NOT'",
    CypherParser.NOTHING -> "'NOTHING'",
    CypherParser.NOWAIT -> "'NOWAIT'",
    CypherParser.NULL -> "'NULL'",
    CypherParser.OF -> "'OF'",
    CypherParser.ON -> "'ON'",
    CypherParser.ONLY -> "'ONLY'",
    CypherParser.OPTIONAL -> "'OPTIONAL'",
    CypherParser.OPTIONS -> "'OPTIONS'",
    CypherParser.OPTION -> "'OPTION'",
    CypherParser.OR -> "'OR'",
    CypherParser.ORDER -> "'ORDER'",
    CypherParser.OUTPUT -> "'OUTPUT'",
    CypherParser.PASSWORD -> "'PASSWORD'",
    CypherParser.PASSWORDS -> "'PASSWORDS'",
    CypherParser.PATH -> "'PATH'",
    CypherParser.PERIODIC -> "'PERIODIC'",
    CypherParser.PLAINTEXT -> "'PLAINTEXT'",
    CypherParser.PLUS -> "'+'",
    CypherParser.PLUSEQUAL -> "'+='",
    CypherParser.POINT -> "'POINT'",
    CypherParser.POPULATED -> "'POPULATED'",
    CypherParser.POW -> "'^'",
    CypherParser.PRIMARY -> "'PRIMARY'",
    CypherParser.PRIVILEGE -> "'PRIVILEGE'",
    CypherParser.PRIVILEGES -> "'PRIVILEGES'",
    CypherParser.PROCEDURE -> "'PROCEDURE'",
    CypherParser.PROCEDURES -> "'PROCEDURES'",
    CypherParser.PROPERTIES -> "'PROPERTIES'",
    CypherParser.PROPERTY -> "'PROPERTY'",
    CypherParser.QUESTION -> "'?'",
    CypherParser.RANGE -> "'RANGE'",
    CypherParser.RBRACKET -> "']'",
    CypherParser.RCURLY -> "'}'",
    CypherParser.READ -> "'READ'",
    CypherParser.REALLOCATE -> "'REALLOCATE'",
    CypherParser.REDUCE -> "'REDUCE'",
    CypherParser.RENAME -> "'RENAME'",
    CypherParser.REGEQ -> "'=~'",
    CypherParser.REL -> "'REL'",
    CypherParser.RELATIONSHIP -> "'RELATIONSHIP'",
    CypherParser.RELATIONSHIPS -> "'RELATIONSHIPS'",
    CypherParser.REMOVE -> "'REMOVE'",
    CypherParser.REPEATABLE -> "'REPEATABLE'",
    CypherParser.REPLACE -> "'REPLACE'",
    CypherParser.REPORT -> "'REPORT'",
    CypherParser.REQUIRE -> "'REQUIRE'",
    CypherParser.REQUIRED -> "'REQUIRED'",
    CypherParser.RETURN -> "'RETURN'",
    CypherParser.REVOKE -> "'REVOKE'",
    CypherParser.ROLE -> "'ROLE'",
    CypherParser.ROLES -> "'ROLES'",
    CypherParser.ROW -> "'ROW'",
    CypherParser.ROWS -> "'ROWS'",
    CypherParser.RPAREN -> "')'",
    CypherParser.SCAN -> "'SCAN'",
    CypherParser.SECONDARY -> "'SECONDARY'",
    CypherParser.SECONDS -> "'SECONDS'",
    CypherParser.SEEK -> "'SEEK'",
    CypherParser.SEMICOLON -> "';'",
    CypherParser.SERVER -> "'SERVER'",
    CypherParser.SERVERS -> "'SERVERS'",
    CypherParser.SET -> "'SET'",
    CypherParser.SETTING -> "'SETTING'",
    CypherParser.SHORTEST_PATH -> "'shortestPath'",
    CypherParser.SHORTEST -> "'SHORTEST'",
    CypherParser.SHOW -> "'SHOW'",
    CypherParser.SIGNED -> "'SIGNED'",
    CypherParser.SINGLE -> "'SINGLE'",
    CypherParser.SKIPROWS -> "'SKIPROWS'",
    CypherParser.START -> "'START'",
    CypherParser.STARTS -> "'STARTS'",
    CypherParser.STATUS -> "'STATUS'",
    CypherParser.STOP -> "'STOP'",
    CypherParser.STRING -> "'STRING'",
    CypherParser.SUPPORTED -> "'SUPPORTED'",
    CypherParser.SUSPENDED -> "'SUSPENDED'",
    CypherParser.TARGET -> "'TARGET'",
    CypherParser.TERMINATE -> "'TERMINATE'",
    CypherParser.TEXT -> "'TEXT'",
    CypherParser.THEN -> "'THEN'",
    CypherParser.TIME -> "'TIME'",
    CypherParser.TIMES -> "'*'",
    CypherParser.TIMESTAMP -> "'TIMESTAMP'",
    CypherParser.TIMEZONE -> "'TIMEZONE'",
    CypherParser.TO -> "'TO'",
    CypherParser.TOPOLOGY -> "'TOPOLOGY'",
    CypherParser.TRAILING -> "'TRAILING'",
    CypherParser.TRANSACTION -> "'TRANSACTION'",
    CypherParser.TRANSACTIONS -> "'TRANSACTIONS'",
    CypherParser.TRAVERSE -> "'TRAVERSE'",
    CypherParser.TRIM -> "'TRIM'",
    CypherParser.TRUE -> "'TRUE'",
    CypherParser.TYPE -> "'TYPE'",
    CypherParser.TYPED -> "'TYPED'",
    CypherParser.TYPES -> "'TYPES'",
    CypherParser.UNION -> "'UNION'",
    CypherParser.UNIQUE -> "'UNIQUE'",
    CypherParser.UNIQUENESS -> "'UNIQUENESS'",
    CypherParser.UNWIND -> "'UNWIND'",
    CypherParser.URL -> "'URL'",
    CypherParser.USE -> "'USE'",
    CypherParser.USER -> "'USER'",
    CypherParser.USERS -> "'USERS'",
    CypherParser.USING -> "'USING'",
    CypherParser.VALUE -> "'VALUE'",
    CypherParser.VECTOR -> "'VECTOR'",
    CypherParser.VERBOSE -> "'VERBOSE'",
    CypherParser.VERTEX -> "'VERTEX'",
    CypherParser.WAIT -> "'WAIT'",
    CypherParser.WHEN -> "'WHEN'",
    CypherParser.WHERE -> "'WHERE'",
    CypherParser.WITH -> "'WITH'",
    CypherParser.WITHOUT -> "'WITHOUT'",
    CypherParser.WRITE -> "'WRITE'",
    CypherParser.XOR -> "'XOR'",
    CypherParser.YIELD -> "'YIELD'",
    CypherParser.ZONED -> "'ZONED'",
    CypherParser.IDENTIFIER -> "an identifier",
    CypherParser.ARROW_LINE -> "'-'",
    CypherParser.ARROW_LEFT_HEAD -> "'<'",
    CypherParser.ARROW_RIGHT_HEAD -> "'>'",
    CypherParser.STRING_LITERAL1 -> "a string value",
    CypherParser.STRING_LITERAL2 -> "a string value",
    CypherParser.MULTI_LINE_COMMENT -> "'/*'",
    CypherParser.ESCAPED_SYMBOLIC_NAME -> "an identifier"
  )
}
