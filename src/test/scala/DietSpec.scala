
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

import net.antoinecomte.jugl.challenge2013.Diet._
import org.scalatest._

class DietSpec extends FlatSpec {

  "Diet" should "find zero output activities" in {

    val in = List(
      Activity("coca-light", 1),
      Activity("croissant", 180),
      Activity("au-travail-a-velo", -113),
      Activity("guitar-hero", -181)
    )
    val out = Seq("croissant", "coca-light", "guitar-hero").sorted
    assert(resolve(in).sorted === out)

  }


}
