/*
 * Copyright [2013] [Antoine comte]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.antoinecomte.jugl.challenge2013

import scala._
import annotation.tailrec

/** Solves the Diet problem */
object Diet {

  case class Activity(name: String, value: Int)

  def resolve(activities: List[Activity]): Seq[String] = {
    val solution: Option[List[Activity]] = combinations(activities).find(healthy(_))
    solution.getOrElse(noSolution).map(_.name)
  }

  private val noSolution = List(Activity("no solution", 0))

  /**
   * Computes a stream of all possible combinations of the supplied list of activity
   * @param x the list of activity
   * @return the stream, beginning with combinations of size 2, then size 3 ...
   */
  private def combinations(x: List[Activity]): Stream[List[Activity]] = {
    @tailrec
    def combinations(c: Int, acc: Stream[List[Activity]]): Stream[List[Activity]] =
      if (c < 2) acc
      else combinations(c - 1, (x.combinations(c).toStream) ++ acc)
    combinations(x.size, Stream())

  }

  /** return true if the sum of values is 0 */
  private def healthy(activities: List[Activity]) = activities.map(_.value).sum == 0

}


