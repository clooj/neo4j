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
package org.neo4j.cypher.internal.ast.factory.neo4j

import org.neo4j.cypher.internal.ast.factory.neo4j.test.util.AstParsingTestBase
import org.neo4j.cypher.internal.ast.factory.neo4j.test.util.LegacyAstParsingTestSupport
import org.neo4j.cypher.internal.expressions._

class NormalizedPredicateParserTest extends AstParsingTestBase with LegacyAstParsingTestSupport {

  test("'string' IS NORMALIZED") {
    parsesTo[Expression] {
      isNormalized(literalString("string"), NFCNormalForm)
    }
  }

  test("'string' IS NOT NORMALIZED") {
    parsesTo[Expression] {
      isNotNormalized(literalString("string"), NFCNormalForm)
    }
  }

  Seq(NFCNormalForm, NFDNormalForm, NFKCNormalForm, NFKDNormalForm).foreach { normalForm =>
    test(s"'string' IS ${normalForm.description} NORMALIZED") {
      parsesTo[Expression] {
        isNormalized(literalString("string"), normalForm)
      }
    }
  }

  Seq(NFCNormalForm, NFDNormalForm, NFKCNormalForm, NFKDNormalForm).foreach { normalForm =>
    test(s"'string' IS NOT ${normalForm.description} NORMALIZED") {
      parsesTo[Expression] {
        isNotNormalized(literalString("string"), normalForm)
      }
    }
  }

  test("'hello ' + 'world'  IS NORMALIZED") {
    parsesTo[Expression] {
      isNormalized(add(literalString("hello "), literalString("world")), NFCNormalForm)
    }
  }
}
