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
package org.neo4j.cypher.internal.logical.plans

import org.neo4j.cypher.internal.ast.ShowColumn
import org.neo4j.cypher.internal.util.attribution.IdGen

abstract class CommandLogicalPlan(idGen: IdGen) extends LogicalLeafPlan(idGen = idGen) {

  def defaultColumns: List[ShowColumn]

  override def usedVariables: Set[String] = Set.empty

  override def withoutArgumentIds(argsToExclude: Set[String]): CommandLogicalPlan = this

  override def addArgumentIds(argsToAdd: Set[String]): LogicalLeafPlan = this

  // Always the first leaf plan, so arguments is always empty
  override def argumentIds: Set[String] = Set.empty

  override def availableSymbols: Set[String] = defaultColumns.map(_.variable.name).toSet
}
