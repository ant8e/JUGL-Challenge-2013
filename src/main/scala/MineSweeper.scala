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

/** Solves the MineSeeper problem */
object MineSweeper {
  type Position = (Int, Int)
  type Positions = Seq[Position]

  /**
   * This class represents the mine field
   * @param width width of the mine field
   * @param height height of the mine field
   * @param mines positions of the mines
   */
  case class MineField(width: Int, height: Int, mines: Positions) {

    /**
     * Test if a position contains a mine
     * @param x x coordinate
     * @param y y coordinate
     * @return true if there is a mine at the specified coordinates
     */
    def isMine(x: Int, y: Int) = mines.contains((x, y))

    /**
     * Returns the list of all neighbors of a specified cell.
     * The result may contain positions "outside" the mine field
     * @param x x coordinate
     * @param y y coordinate
     *
     */
    def neighbors(x: Int, y: Int) = Seq(
      (x - 1, y - 1), (x, y - 1), (x + 1, y - 1),
      (x - 1, y), (x, y), (x + 1, y),
      (x - 1, y + 1), (x, y + 1), (x + 1, y + 1)
    )

    /**
     * Returns the count of mines around a specified cell.
     * @param x x coordinate
     * @param y y coordinate
     *
     */
    def countMinesAround(x: Int, y: Int) = (
      for ((x1, y1) <- neighbors(x, y)
           if isMine(x1, y1))
      yield 1
      ).sum
  }

  /**
   * Parses the input
   * @param s the input string
   * @return a MineField
   */
  def parse(s: String): MineField = {
    /** lines of input, trimmed and without empty lines */
    val lines = s.lines.toList.map(_.trim).filter(!_.isEmpty)

    /** convert the first line to an array, splitting around white space */
    val split: Array[String] = lines.head.split(' ')

    /** extracting the minefield dimensions */
    val (h, w) = (split(0).toInt, split(1).toInt)

    /** finding mines and their coordinates */
    val mines = for (l <- lines.tail.zip(0 until h);
                     c <- l._1.zip(0 until w) if (c._1 == '*')) yield (c._2, l._2)

    MineField(w, h, mines)
  }

  /** Solving the minefield */
  def resolve(field: MineField): String = {

    /** At this point, we just have to count the mines around each position */
    val solution = for (y <- 0 until field.height;
                        x <- 0 until field.width)
    yield if (field.isMine(x, y)) '*'
      else field.countMinesAround(x, y).toString

    /** format the solution to give the result */
    solution.mkString.
      grouped(field.width).
      mkString("", "\n", "")
  }

  def countMines(in: String): String = resolve(parse(in))
}

