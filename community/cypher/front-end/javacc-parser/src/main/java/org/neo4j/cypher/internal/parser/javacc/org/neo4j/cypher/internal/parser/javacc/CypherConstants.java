/* Generated by: ParserGeneratorCC: Do not edit this line. CypherConstants.java */
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
package org.neo4j.cypher.internal.parser.javacc;


/**
 * Token literal values and constants.
 * Generated by com.helger.pgcc.output.java.OtherFilesGenJava#start()
 */
public interface CypherConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int SINGLE_LINE_COMMENT = 29;
  /** RegularExpression Id. */
  int FORMAL_COMMENT = 32;
  /** RegularExpression Id. */
  int MULTI_LINE_COMMENT = 33;
  /** RegularExpression Id. */
  int DECIMAL_DOUBLE = 35;
  /** RegularExpression Id. */
  int UNSIGNED_DECIMAL_INTEGER = 36;
  /** RegularExpression Id. */
  int DECIMAL_EXPONENT = 37;
  /** RegularExpression Id. */
  int INTEGER_PART = 38;
  /** RegularExpression Id. */
  int UNSIGNED_HEX_INTEGER = 39;
  /** RegularExpression Id. */
  int UNSIGNED_OCTAL_INTEGER = 40;
  /** RegularExpression Id. */
  int STRING1_OPEN = 41;
  /** RegularExpression Id. */
  int STRING_LITERAL1 = 52;
  /** RegularExpression Id. */
  int STRING2_OPEN = 53;
  /** RegularExpression Id. */
  int STRING_LITERAL2 = 64;
  /** RegularExpression Id. */
  int ESCAPED_SYMBOLIC_NAME_OPEN = 65;
  /** RegularExpression Id. */
  int ESCAPED_SYMBOLIC_NAME = 68;
  /** RegularExpression Id. */
  int ACCESS = 69;
  /** RegularExpression Id. */
  int ACTIVE = 70;
  /** RegularExpression Id. */
  int ADMIN = 71;
  /** RegularExpression Id. */
  int ADMINISTRATOR = 72;
  /** RegularExpression Id. */
  int ALIAS = 73;
  /** RegularExpression Id. */
  int ALIASES = 74;
  /** RegularExpression Id. */
  int ALL_SHORTEST_PATH = 75;
  /** RegularExpression Id. */
  int ALL = 76;
  /** RegularExpression Id. */
  int ALTER = 77;
  /** RegularExpression Id. */
  int AND = 78;
  /** RegularExpression Id. */
  int ANY = 79;
  /** RegularExpression Id. */
  int ARRAY = 80;
  /** RegularExpression Id. */
  int AS = 81;
  /** RegularExpression Id. */
  int ASC = 82;
  /** RegularExpression Id. */
  int ASCENDING = 83;
  /** RegularExpression Id. */
  int ASSERT = 84;
  /** RegularExpression Id. */
  int ASSIGN = 85;
  /** RegularExpression Id. */
  int AT = 86;
  /** RegularExpression Id. */
  int BAR = 87;
  /** RegularExpression Id. */
  int BINDINGS = 88;
  /** RegularExpression Id. */
  int BOOL = 89;
  /** RegularExpression Id. */
  int BOOLEAN = 90;
  /** RegularExpression Id. */
  int BOOSTED = 91;
  /** RegularExpression Id. */
  int BREAK = 92;
  /** RegularExpression Id. */
  int BRIEF = 93;
  /** RegularExpression Id. */
  int BTREE = 94;
  /** RegularExpression Id. */
  int BUILT = 95;
  /** RegularExpression Id. */
  int BY = 96;
  /** RegularExpression Id. */
  int CALL = 97;
  /** RegularExpression Id. */
  int CASE = 98;
  /** RegularExpression Id. */
  int CHANGE = 99;
  /** RegularExpression Id. */
  int CIDR = 100;
  /** RegularExpression Id. */
  int COLLECT = 101;
  /** RegularExpression Id. */
  int COLON = 102;
  /** RegularExpression Id. */
  int COLONCOLON = 103;
  /** RegularExpression Id. */
  int COMMA = 104;
  /** RegularExpression Id. */
  int COMMAND = 105;
  /** RegularExpression Id. */
  int COMMANDS = 106;
  /** RegularExpression Id. */
  int COMMIT = 107;
  /** RegularExpression Id. */
  int COMPOSITE = 108;
  /** RegularExpression Id. */
  int CONCURRENT = 109;
  /** RegularExpression Id. */
  int CONSTRAINT = 110;
  /** RegularExpression Id. */
  int CONSTRAINTS = 111;
  /** RegularExpression Id. */
  int CONTAINS = 112;
  /** RegularExpression Id. */
  int COPY = 113;
  /** RegularExpression Id. */
  int CONTINUE = 114;
  /** RegularExpression Id. */
  int COUNT = 115;
  /** RegularExpression Id. */
  int CREATE = 116;
  /** RegularExpression Id. */
  int CSV = 117;
  /** RegularExpression Id. */
  int CURRENT = 118;
  /** RegularExpression Id. */
  int DATA = 119;
  /** RegularExpression Id. */
  int DATABASE = 120;
  /** RegularExpression Id. */
  int DATABASES = 121;
  /** RegularExpression Id. */
  int DATE = 122;
  /** RegularExpression Id. */
  int DATETIME = 123;
  /** RegularExpression Id. */
  int DBMS = 124;
  /** RegularExpression Id. */
  int DEALLOCATE = 125;
  /** RegularExpression Id. */
  int DEFAULT_TOKEN = 126;
  /** RegularExpression Id. */
  int DEFINED = 127;
  /** RegularExpression Id. */
  int DELETE = 128;
  /** RegularExpression Id. */
  int DENY = 129;
  /** RegularExpression Id. */
  int DESC = 130;
  /** RegularExpression Id. */
  int DESCENDING = 131;
  /** RegularExpression Id. */
  int DESTROY = 132;
  /** RegularExpression Id. */
  int DETACH = 133;
  /** RegularExpression Id. */
  int DOLLAR = 134;
  /** RegularExpression Id. */
  int DOUBLEBAR = 135;
  /** RegularExpression Id. */
  int DIFFERENT = 136;
  /** RegularExpression Id. */
  int DISTINCT = 137;
  /** RegularExpression Id. */
  int DIVIDE = 138;
  /** RegularExpression Id. */
  int DOT = 139;
  /** RegularExpression Id. */
  int DOTDOT = 140;
  /** RegularExpression Id. */
  int DRIVER = 141;
  /** RegularExpression Id. */
  int DROP = 142;
  /** RegularExpression Id. */
  int DRYRUN = 143;
  /** RegularExpression Id. */
  int DUMP = 144;
  /** RegularExpression Id. */
  int DURATION = 145;
  /** RegularExpression Id. */
  int EACH = 146;
  /** RegularExpression Id. */
  int EDGE = 147;
  /** RegularExpression Id. */
  int ENABLE = 148;
  /** RegularExpression Id. */
  int ELEMENT = 149;
  /** RegularExpression Id. */
  int ELEMENTS = 150;
  /** RegularExpression Id. */
  int ELSE = 151;
  /** RegularExpression Id. */
  int ENCRYPTED = 152;
  /** RegularExpression Id. */
  int END = 153;
  /** RegularExpression Id. */
  int ENDS = 154;
  /** RegularExpression Id. */
  int EQ = 155;
  /** RegularExpression Id. */
  int EXECUTABLE = 156;
  /** RegularExpression Id. */
  int EXECUTE = 157;
  /** RegularExpression Id. */
  int EXIST = 158;
  /** RegularExpression Id. */
  int EXISTENCE = 159;
  /** RegularExpression Id. */
  int EXISTS = 160;
  /** RegularExpression Id. */
  int ERROR = 161;
  /** RegularExpression Id. */
  int FAIL = 162;
  /** RegularExpression Id. */
  int FALSE = 163;
  /** RegularExpression Id. */
  int FIELDTERMINATOR = 164;
  /** RegularExpression Id. */
  int FINISH = 165;
  /** RegularExpression Id. */
  int FLOAT = 166;
  /** RegularExpression Id. */
  int FOR = 167;
  /** RegularExpression Id. */
  int FOREACH = 168;
  /** RegularExpression Id. */
  int FROM = 169;
  /** RegularExpression Id. */
  int FULLTEXT = 170;
  /** RegularExpression Id. */
  int FUNCTION = 171;
  /** RegularExpression Id. */
  int FUNCTIONS = 172;
  /** RegularExpression Id. */
  int GE = 173;
  /** RegularExpression Id. */
  int GRANT = 174;
  /** RegularExpression Id. */
  int GRAPH = 175;
  /** RegularExpression Id. */
  int GRAPHS = 176;
  /** RegularExpression Id. */
  int GROUP = 177;
  /** RegularExpression Id. */
  int GROUPS = 178;
  /** RegularExpression Id. */
  int GT = 179;
  /** RegularExpression Id. */
  int HEADERS = 180;
  /** RegularExpression Id. */
  int HOME = 181;
  /** RegularExpression Id. */
  int IF = 182;
  /** RegularExpression Id. */
  int IMPERSONATE = 183;
  /** RegularExpression Id. */
  int IMMUTABLE = 184;
  /** RegularExpression Id. */
  int IN = 185;
  /** RegularExpression Id. */
  int INDEX = 186;
  /** RegularExpression Id. */
  int INDEXES = 187;
  /** RegularExpression Id. */
  int INF = 188;
  /** RegularExpression Id. */
  int INFINITY = 189;
  /** RegularExpression Id. */
  int INSERT = 190;
  /** RegularExpression Id. */
  int INT = 191;
  /** RegularExpression Id. */
  int INTEGER = 192;
  /** RegularExpression Id. */
  int IS = 193;
  /** RegularExpression Id. */
  int JOIN = 194;
  /** RegularExpression Id. */
  int KEY = 195;
  /** RegularExpression Id. */
  int LABEL = 196;
  /** RegularExpression Id. */
  int LABELS = 197;
  /** RegularExpression Id. */
  int AMPERSAND = 198;
  /** RegularExpression Id. */
  int EXCLAMATION_MARK = 199;
  /** RegularExpression Id. */
  int LBRACKET = 200;
  /** RegularExpression Id. */
  int LCURLY = 201;
  /** RegularExpression Id. */
  int LE = 202;
  /** RegularExpression Id. */
  int LIMITROWS = 203;
  /** RegularExpression Id. */
  int LIST = 204;
  /** RegularExpression Id. */
  int LOAD = 205;
  /** RegularExpression Id. */
  int LOCAL = 206;
  /** RegularExpression Id. */
  int LOOKUP = 207;
  /** RegularExpression Id. */
  int LPAREN = 208;
  /** RegularExpression Id. */
  int LT = 209;
  /** RegularExpression Id. */
  int MANAGEMENT = 210;
  /** RegularExpression Id. */
  int MAP = 211;
  /** RegularExpression Id. */
  int MATCH = 212;
  /** RegularExpression Id. */
  int MERGE = 213;
  /** RegularExpression Id. */
  int MINUS = 214;
  /** RegularExpression Id. */
  int PERCENT = 215;
  /** RegularExpression Id. */
  int NEQ = 216;
  /** RegularExpression Id. */
  int NEQ2 = 217;
  /** RegularExpression Id. */
  int NAME = 218;
  /** RegularExpression Id. */
  int NAMES = 219;
  /** RegularExpression Id. */
  int NAN = 220;
  /** RegularExpression Id. */
  int NFC = 221;
  /** RegularExpression Id. */
  int NFD = 222;
  /** RegularExpression Id. */
  int NFKC = 223;
  /** RegularExpression Id. */
  int NFKD = 224;
  /** RegularExpression Id. */
  int NEW = 225;
  /** RegularExpression Id. */
  int NODE = 226;
  /** RegularExpression Id. */
  int NODETACH = 227;
  /** RegularExpression Id. */
  int NODES = 228;
  /** RegularExpression Id. */
  int NONE = 229;
  /** RegularExpression Id. */
  int NORMALIZE = 230;
  /** RegularExpression Id. */
  int NORMALIZED = 231;
  /** RegularExpression Id. */
  int NOT = 232;
  /** RegularExpression Id. */
  int NOTHING = 233;
  /** RegularExpression Id. */
  int NOWAIT = 234;
  /** RegularExpression Id. */
  int NULL = 235;
  /** RegularExpression Id. */
  int OF = 236;
  /** RegularExpression Id. */
  int ON = 237;
  /** RegularExpression Id. */
  int ONLY = 238;
  /** RegularExpression Id. */
  int OPTIONAL = 239;
  /** RegularExpression Id. */
  int OPTIONS = 240;
  /** RegularExpression Id. */
  int OPTION = 241;
  /** RegularExpression Id. */
  int OR = 242;
  /** RegularExpression Id. */
  int ORDER = 243;
  /** RegularExpression Id. */
  int OUTPUT = 244;
  /** RegularExpression Id. */
  int PASSWORD = 245;
  /** RegularExpression Id. */
  int PASSWORDS = 246;
  /** RegularExpression Id. */
  int PATH = 247;
  /** RegularExpression Id. */
  int PATHS = 248;
  /** RegularExpression Id. */
  int PERIODIC = 249;
  /** RegularExpression Id. */
  int PLAINTEXT = 250;
  /** RegularExpression Id. */
  int PLUS = 251;
  /** RegularExpression Id. */
  int PLUSEQUAL = 252;
  /** RegularExpression Id. */
  int POINT = 253;
  /** RegularExpression Id. */
  int POPULATED = 254;
  /** RegularExpression Id. */
  int POW = 255;
  /** RegularExpression Id. */
  int REPEATABLE = 256;
  /** RegularExpression Id. */
  int PRIMARY = 257;
  /** RegularExpression Id. */
  int PRIMARIES = 258;
  /** RegularExpression Id. */
  int PRIVILEGE = 259;
  /** RegularExpression Id. */
  int PRIVILEGES = 260;
  /** RegularExpression Id. */
  int PROCEDURE = 261;
  /** RegularExpression Id. */
  int PROCEDURES = 262;
  /** RegularExpression Id. */
  int PROPERTIES = 263;
  /** RegularExpression Id. */
  int PROPERTY = 264;
  /** RegularExpression Id. */
  int QUESTION = 265;
  /** RegularExpression Id. */
  int RANGE = 266;
  /** RegularExpression Id. */
  int RBRACKET = 267;
  /** RegularExpression Id. */
  int RCURLY = 268;
  /** RegularExpression Id. */
  int READ = 269;
  /** RegularExpression Id. */
  int REALLOCATE = 270;
  /** RegularExpression Id. */
  int REDUCE = 271;
  /** RegularExpression Id. */
  int RENAME = 272;
  /** RegularExpression Id. */
  int REGEQ = 273;
  /** RegularExpression Id. */
  int REL = 274;
  /** RegularExpression Id. */
  int RELATIONSHIP = 275;
  /** RegularExpression Id. */
  int RELATIONSHIPS = 276;
  /** RegularExpression Id. */
  int REMOVE = 277;
  /** RegularExpression Id. */
  int REPLACE = 278;
  /** RegularExpression Id. */
  int REPORT = 279;
  /** RegularExpression Id. */
  int REQUIRE = 280;
  /** RegularExpression Id. */
  int REQUIRED = 281;
  /** RegularExpression Id. */
  int RETURN = 282;
  /** RegularExpression Id. */
  int REVOKE = 283;
  /** RegularExpression Id. */
  int ROLE = 284;
  /** RegularExpression Id. */
  int ROLES = 285;
  /** RegularExpression Id. */
  int ROW = 286;
  /** RegularExpression Id. */
  int ROWS = 287;
  /** RegularExpression Id. */
  int RPAREN = 288;
  /** RegularExpression Id. */
  int SCAN = 289;
  /** RegularExpression Id. */
  int SEC = 290;
  /** RegularExpression Id. */
  int SECOND = 291;
  /** RegularExpression Id. */
  int SECONDARY = 292;
  /** RegularExpression Id. */
  int SECONDARIES = 293;
  /** RegularExpression Id. */
  int SECONDS = 294;
  /** RegularExpression Id. */
  int SEEK = 295;
  /** RegularExpression Id. */
  int SEMICOLON = 296;
  /** RegularExpression Id. */
  int SERVER = 297;
  /** RegularExpression Id. */
  int SERVERS = 298;
  /** RegularExpression Id. */
  int SET = 299;
  /** RegularExpression Id. */
  int SETTING = 300;
  /** RegularExpression Id. */
  int SETTINGS = 301;
  /** RegularExpression Id. */
  int SHORTEST_PATH = 302;
  /** RegularExpression Id. */
  int SHORTEST = 303;
  /** RegularExpression Id. */
  int SHOW = 304;
  /** RegularExpression Id. */
  int SIGNED = 305;
  /** RegularExpression Id. */
  int SINGLE = 306;
  /** RegularExpression Id. */
  int SKIPROWS = 307;
  /** RegularExpression Id. */
  int START = 308;
  /** RegularExpression Id. */
  int STARTS = 309;
  /** RegularExpression Id. */
  int STATUS = 310;
  /** RegularExpression Id. */
  int STOP = 311;
  /** RegularExpression Id. */
  int STRING = 312;
  /** RegularExpression Id. */
  int SUPPORTED = 313;
  /** RegularExpression Id. */
  int SUSPENDED = 314;
  /** RegularExpression Id. */
  int TARGET = 315;
  /** RegularExpression Id. */
  int TERMINATE = 316;
  /** RegularExpression Id. */
  int TEXT = 317;
  /** RegularExpression Id. */
  int THEN = 318;
  /** RegularExpression Id. */
  int TIME = 319;
  /** RegularExpression Id. */
  int TIMES = 320;
  /** RegularExpression Id. */
  int TIMESTAMP = 321;
  /** RegularExpression Id. */
  int TIMEZONE = 322;
  /** RegularExpression Id. */
  int TO = 323;
  /** RegularExpression Id. */
  int TOPOLOGY = 324;
  /** RegularExpression Id. */
  int TRANSACTION = 325;
  /** RegularExpression Id. */
  int TRANSACTIONS = 326;
  /** RegularExpression Id. */
  int TRAVERSE = 327;
  /** RegularExpression Id. */
  int TRUE = 328;
  /** RegularExpression Id. */
  int TYPE = 329;
  /** RegularExpression Id. */
  int TYPED = 330;
  /** RegularExpression Id. */
  int TYPES = 331;
  /** RegularExpression Id. */
  int UNION = 332;
  /** RegularExpression Id. */
  int UNIQUE = 333;
  /** RegularExpression Id. */
  int UNIQUENESS = 334;
  /** RegularExpression Id. */
  int UNWIND = 335;
  /** RegularExpression Id. */
  int URL = 336;
  /** RegularExpression Id. */
  int USE = 337;
  /** RegularExpression Id. */
  int USER = 338;
  /** RegularExpression Id. */
  int USERS = 339;
  /** RegularExpression Id. */
  int USING = 340;
  /** RegularExpression Id. */
  int VALUE = 341;
  /** RegularExpression Id. */
  int VARCHAR = 342;
  /** RegularExpression Id. */
  int VECTOR = 343;
  /** RegularExpression Id. */
  int VERBOSE = 344;
  /** RegularExpression Id. */
  int VERTEX = 345;
  /** RegularExpression Id. */
  int WAIT = 346;
  /** RegularExpression Id. */
  int WHEN = 347;
  /** RegularExpression Id. */
  int WHERE = 348;
  /** RegularExpression Id. */
  int WITH = 349;
  /** RegularExpression Id. */
  int WITHOUT = 350;
  /** RegularExpression Id. */
  int WRITE = 351;
  /** RegularExpression Id. */
  int XOR = 352;
  /** RegularExpression Id. */
  int YIELD = 353;
  /** RegularExpression Id. */
  int ZONED = 354;
  /** RegularExpression Id. */
  int IDENTIFIER = 355;
  /** RegularExpression Id. */
  int LETTER = 356;
  /** RegularExpression Id. */
  int PART_LETTER = 357;
  /** RegularExpression Id. */
  int ARROW_LINE = 358;
  /** RegularExpression Id. */
  int ARROW_LEFT_HEAD = 359;
  /** RegularExpression Id. */
  int ARROW_RIGHT_HEAD = 360;
  /** RegularExpression Id. */
  int UNKNOWN = 361;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int IN_FORMAL_COMMENT = 1;
  /** Lexical state. */
  int IN_MULTI_LINE_COMMENT = 2;
  /** Lexical state. */
  int IN_SINGLE_LINE_COMMENT = 3;
  /** Lexical state. */
  int STRING1 = 4;
  /** Lexical state. */
  int STRING2 = 5;
  /** Lexical state. */
  int ESC_SYMB_NAME = 6;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\u000b\"",
    "\"\\f\"",
    "\"\\r\"",
    "\"\\u001c\"",
    "\"\\u001d\"",
    "\"\\u001e\"",
    "\"\\u001f\"",
    "\" \"",
    "\"\\u00a0\"",
    "\"\\u1680\"",
    "\"\\u2000\"",
    "\"\\u2001\"",
    "\"\\u2002\"",
    "\"\\u2003\"",
    "\"\\u2004\"",
    "\"\\u2005\"",
    "\"\\u2006\"",
    "\"\\u2007\"",
    "\"\\u2008\"",
    "\"\\u2009\"",
    "\"\\u200a\"",
    "\"\\u2028\"",
    "\"\\u2029\"",
    "\"\\u202f\"",
    "\"\\u205f\"",
    "\"\\u3000\"",
    "<SINGLE_LINE_COMMENT>",
    "<token of kind 30>",
    "\"/*\"",
    "\"*/\"",
    "\"*/\"",
    "<token of kind 34>",
    "<DECIMAL_DOUBLE>",
    "<UNSIGNED_DECIMAL_INTEGER>",
    "<DECIMAL_EXPONENT>",
    "<INTEGER_PART>",
    "<UNSIGNED_HEX_INTEGER>",
    "<UNSIGNED_OCTAL_INTEGER>",
    "\"\\\'\"",
    "\"\\\\\\\\\"",
    "\"\\\\\\\'\"",
    "\"\\\\\\\"\"",
    "\"\\\\b\"",
    "\"\\\\f\"",
    "\"\\\\n\"",
    "\"\\\\r\"",
    "\"\\\\t\"",
    "\"\\\\u[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]\"",
    "<token of kind 51>",
    "\"\\\'\"",
    "\"\\\"\"",
    "\"\\\\\\\\\"",
    "\"\\\\\\\'\"",
    "\"\\\\\\\"\"",
    "\"\\\\b\"",
    "\"\\\\f\"",
    "\"\\\\n\"",
    "\"\\\\r\"",
    "\"\\\\t\"",
    "\"\\\\u[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]\"",
    "<token of kind 63>",
    "\"\\\"\"",
    "\"`\"",
    "<token of kind 66>",
    "\"``\"",
    "\"`\"",
    "\"ACCESS\"",
    "\"ACTIVE\"",
    "\"ADMIN\"",
    "\"ADMINISTRATOR\"",
    "\"ALIAS\"",
    "\"ALIASES\"",
    "\"allShortestPaths\"",
    "\"ALL\"",
    "\"ALTER\"",
    "\"AND\"",
    "\"ANY\"",
    "\"ARRAY\"",
    "\"AS\"",
    "\"ASC\"",
    "\"ASCENDING\"",
    "\"ASSERT\"",
    "\"ASSIGN\"",
    "\"AT\"",
    "\"|\"",
    "\"BINDINGS\"",
    "\"BOOL\"",
    "\"BOOLEAN\"",
    "\"BOOSTED\"",
    "\"BREAK\"",
    "\"BRIEF\"",
    "\"BTREE\"",
    "\"BUILT\"",
    "\"BY\"",
    "\"CALL\"",
    "\"CASE\"",
    "\"CHANGE\"",
    "\"CIDR\"",
    "\"COLLECT\"",
    "\":\"",
    "\"::\"",
    "\",\"",
    "\"COMMAND\"",
    "\"COMMANDS\"",
    "\"COMMIT\"",
    "\"COMPOSITE\"",
    "\"CONCURRENT\"",
    "\"CONSTRAINT\"",
    "\"CONSTRAINTS\"",
    "\"CONTAINS\"",
    "\"COPY\"",
    "\"CONTINUE\"",
    "\"COUNT\"",
    "\"CREATE\"",
    "\"CSV\"",
    "\"CURRENT\"",
    "\"DATA\"",
    "\"DATABASE\"",
    "\"DATABASES\"",
    "\"DATE\"",
    "\"DATETIME\"",
    "\"DBMS\"",
    "\"DEALLOCATE\"",
    "\"DEFAULT\"",
    "\"DEFINED\"",
    "\"DELETE\"",
    "\"DENY\"",
    "\"DESC\"",
    "\"DESCENDING\"",
    "\"DESTROY\"",
    "\"DETACH\"",
    "\"$\"",
    "\"||\"",
    "\"DIFFERENT\"",
    "\"DISTINCT\"",
    "\"/\"",
    "\".\"",
    "\"..\"",
    "\"DRIVER\"",
    "\"DROP\"",
    "\"DRYRUN\"",
    "\"DUMP\"",
    "\"DURATION\"",
    "\"EACH\"",
    "\"EDGE\"",
    "\"ENABLE\"",
    "\"ELEMENT\"",
    "\"ELEMENTS\"",
    "\"ELSE\"",
    "\"ENCRYPTED\"",
    "\"END\"",
    "\"ENDS\"",
    "\"=\"",
    "\"EXECUTABLE\"",
    "\"EXECUTE\"",
    "\"EXIST\"",
    "\"EXISTENCE\"",
    "\"EXISTS\"",
    "\"ERROR\"",
    "\"FAIL\"",
    "\"false\"",
    "\"FIELDTERMINATOR\"",
    "\"FINISH\"",
    "\"FLOAT\"",
    "\"FOR\"",
    "\"FOREACH\"",
    "\"FROM\"",
    "\"FULLTEXT\"",
    "\"FUNCTION\"",
    "\"FUNCTIONS\"",
    "\">=\"",
    "\"GRANT\"",
    "\"GRAPH\"",
    "\"GRAPHS\"",
    "\"GROUP\"",
    "\"GROUPS\"",
    "\">\"",
    "\"HEADERS\"",
    "\"HOME\"",
    "\"IF\"",
    "\"IMPERSONATE\"",
    "\"IMMUTABLE\"",
    "\"IN\"",
    "\"INDEX\"",
    "\"INDEXES\"",
    "\"INF\"",
    "\"INFINITY\"",
    "\"INSERT\"",
    "\"INT\"",
    "\"INTEGER\"",
    "\"IS\"",
    "\"JOIN\"",
    "\"KEY\"",
    "\"LABEL\"",
    "\"LABELS\"",
    "\"&\"",
    "\"!\"",
    "\"[\"",
    "\"{\"",
    "\"<=\"",
    "\"LIMIT\"",
    "\"LIST\"",
    "\"LOAD\"",
    "\"LOCAL\"",
    "\"LOOKUP\"",
    "\"(\"",
    "\"<\"",
    "\"MANAGEMENT\"",
    "\"MAP\"",
    "\"MATCH\"",
    "\"MERGE\"",
    "\"-\"",
    "\"%\"",
    "\"!=\"",
    "\"<>\"",
    "\"NAME\"",
    "\"NAMES\"",
    "\"NAN\"",
    "\"NFC\"",
    "\"NFD\"",
    "\"NFKC\"",
    "\"NFKD\"",
    "\"NEW\"",
    "\"NODE\"",
    "\"NODETACH\"",
    "\"NODES\"",
    "\"NONE\"",
    "\"NORMALIZE\"",
    "\"NORMALIZED\"",
    "\"NOT\"",
    "\"NOTHING\"",
    "\"NOWAIT\"",
    "\"null\"",
    "\"OF\"",
    "\"ON\"",
    "\"ONLY\"",
    "\"OPTIONAL\"",
    "\"OPTIONS\"",
    "\"OPTION\"",
    "\"OR\"",
    "\"ORDER\"",
    "\"OUTPUT\"",
    "\"PASSWORD\"",
    "\"PASSWORDS\"",
    "\"PATH\"",
    "\"PATHS\"",
    "\"PERIODIC\"",
    "\"PLAINTEXT\"",
    "\"+\"",
    "\"+=\"",
    "\"POINT\"",
    "\"POPULATED\"",
    "\"^\"",
    "\"REPEATABLE\"",
    "\"PRIMARY\"",
    "\"PRIMARIES\"",
    "\"PRIVILEGE\"",
    "\"PRIVILEGES\"",
    "\"PROCEDURE\"",
    "\"PROCEDURES\"",
    "\"PROPERTIES\"",
    "\"PROPERTY\"",
    "\"?\"",
    "\"RANGE\"",
    "\"]\"",
    "\"}\"",
    "\"READ\"",
    "\"REALLOCATE\"",
    "\"REDUCE\"",
    "\"RENAME\"",
    "\"=~\"",
    "\"REL\"",
    "\"RELATIONSHIP\"",
    "\"RELATIONSHIPS\"",
    "\"REMOVE\"",
    "\"REPLACE\"",
    "\"REPORT\"",
    "\"REQUIRE\"",
    "\"REQUIRED\"",
    "\"RETURN\"",
    "\"REVOKE\"",
    "\"ROLE\"",
    "\"ROLES\"",
    "\"ROW\"",
    "\"ROWS\"",
    "\")\"",
    "\"SCAN\"",
    "\"SEC\"",
    "\"SECOND\"",
    "\"SECONDARY\"",
    "\"SECONDARIES\"",
    "\"SECONDS\"",
    "\"SEEK\"",
    "\";\"",
    "\"SERVER\"",
    "\"SERVERS\"",
    "\"SET\"",
    "\"SETTING\"",
    "\"SETTINGS\"",
    "\"shortestPath\"",
    "\"SHORTEST\"",
    "\"SHOW\"",
    "\"SIGNED\"",
    "\"SINGLE\"",
    "\"SKIP\"",
    "\"START\"",
    "\"STARTS\"",
    "\"STATUS\"",
    "\"STOP\"",
    "\"STRING\"",
    "\"SUPPORTED\"",
    "\"SUSPENDED\"",
    "\"TARGET\"",
    "\"TERMINATE\"",
    "\"TEXT\"",
    "\"THEN\"",
    "\"TIME\"",
    "\"*\"",
    "\"TIMESTAMP\"",
    "\"TIMEZONE\"",
    "\"TO\"",
    "\"TOPOLOGY\"",
    "\"TRANSACTION\"",
    "\"TRANSACTIONS\"",
    "\"TRAVERSE\"",
    "\"true\"",
    "\"TYPE\"",
    "\"TYPED\"",
    "\"TYPES\"",
    "\"UNION\"",
    "\"UNIQUE\"",
    "\"UNIQUENESS\"",
    "\"UNWIND\"",
    "\"URL\"",
    "\"USE\"",
    "\"USER\"",
    "\"USERS\"",
    "\"USING\"",
    "\"VALUE\"",
    "\"VARCHAR\"",
    "\"VECTOR\"",
    "\"VERBOSE\"",
    "\"VERTEX\"",
    "\"WAIT\"",
    "\"WHEN\"",
    "\"WHERE\"",
    "\"WITH\"",
    "\"WITHOUT\"",
    "\"WRITE\"",
    "\"XOR\"",
    "\"YIELD\"",
    "\"ZONED\"",
    "<IDENTIFIER>",
    "<LETTER>",
    "<PART_LETTER>",
    "<ARROW_LINE>",
    "<ARROW_LEFT_HEAD>",
    "<ARROW_RIGHT_HEAD>",
    "<UNKNOWN>",
  };

}
