package mpbostock

import kotlin.collections.drop
import kotlin.collections.flatMap
import kotlin.collections.indices
import kotlin.collections.map
import kotlin.collections.mapIndexed
import kotlin.collections.plus
import kotlin.collections.toSet
import kotlin.collections.toTypedArray
import kotlin.ranges.contains
import kotlin.ranges.until
import kotlin.text.mapIndexed

typealias Vec2d = Coordinate

interface Grid<T> {
  val cells: Array<Array<PositionedCell<T>>>
  val defaultCell: T
  val width: Int
    get() = cells[0].size
  val height: Int
    get() = cells.size
  val coordinates: Set<Coordinate>
  val coordinatesOfInterest: Set<Coordinate>
  fun getCell(pos: Coordinate) = if (validPos(pos)) cells[pos.y][pos.x].cell else defaultCell
  private fun validPos(pos: Coordinate) = pos.x in 0 until width && pos.y in 0 until height
  fun Coordinate.move(mover: PositionMover) = getCell(mover.move(this))

  fun print(coordinateMapper: (Coordinate) -> Char) {
    (0 until height).onEach { y ->
      (0 until width).onEach { x ->
        val coordinate = Coordinate(x, y)
        print(coordinateMapper(coordinate))
      }
      println()
    }
  }

  companion object {
    inline fun <reified T> fromInput(
      input: List<String>,
      defaultCell: T,
      crossinline charMapper: (Char) -> T,
      crossinline isCellInteresting: (Char, Coordinate) -> Boolean = { _, _ -> false }
    ): Grid<T> {
      val coordinatesOfInterest = mutableSetOf<Coordinate>()
      val cells = input.mapIndexed { y, line ->
        line.mapIndexed { x, c ->
          val pos = Coordinate(x, y)
          if (isCellInteresting(c, pos)) {
            coordinatesOfInterest.add(pos)
          }
          PositionedCell(pos, charMapper(c))
        }.toTypedArray()
      }.toTypedArray()
      return object : Grid<T> {
        override val cells: Array<Array<PositionedCell<T>>>
          get() = cells
        override val defaultCell: T
          get() = defaultCell
        override val coordinates: Set<Coordinate>
          get() = cells.flatMap { row -> row.map { it.pos } }.toSet()
        override val coordinatesOfInterest: Set<Coordinate>
          get() = coordinatesOfInterest

      }
    }
  }
}

data class Coordinate(val x: Int, val y: Int) {
  operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
  operator fun minus(other: Coordinate) = Coordinate(x - other.x, y - other.y)
  companion object {
    val Origin = Coordinate(0, 0)
  }
}

fun Coordinate.wrapTo(width: Int, height: Int): Coordinate {
  val newX = when {
    x < 0 -> x + width
    x >= width -> x - width
    else -> x
  }
  val newY = when {
    y < 0 -> y + height
    y >= height -> y - height
    else -> y
  }
  return Coordinate(newX, newY)
}

data class PositionedCell<T>(val pos: Coordinate, val cell: T)
fun interface PositionMover {
  fun move(pos: Coordinate): Coordinate
}

fun List<Coordinate>.toLines(): List<Line> {
  tailrec fun getLines(index: Int, otherIndices: List<Int>, acc: List<Line>): List<Line> {
    return when {
      otherIndices.isEmpty() -> acc
      else -> {
        getLines(index + 1, otherIndices.drop(1), acc + otherIndices.map { Line(this[index], this[it]) })
      }
    }
  }
  return getLines(0, this.indices.drop(1), emptyList())
}

data class Line(val start: Coordinate, val end: Coordinate) {
  fun deltaXDeltaY() = Coordinate(end.x - start.x, end.y - start.y)
}

fun Int.isOdd(): Boolean = this % 2 == 1
//fun Long.numDigits(): Int = when (this) {
//  contains 0..9 -> 1
//  else -> 1 + (this / 10).numDigits()
//}
//
//fun Long.splitInHalf(numDigits: Int = this.numDigits()): List<Long> {
//  var divisor = 1
//  for (i in 0 until numDigits / 2) {
//    divisor *= 10
//  }
//  return listOf(this / divisor, this % divisor)
//}
enum class Direction : PositionMover {
  North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest;

  override fun move(pos: Coordinate): Coordinate {
    return when (this) {
      North -> pos.copy(y = pos.y - 1)
      NorthEast -> pos.copy(x = pos.x + 1, y = pos.y - 1)
      East -> pos.copy(x = pos.x + 1)
      SouthEast -> pos.copy(x = pos.x + 1, y = pos.y + 1)
      South -> pos.copy(y = pos.y + 1)
      SouthWest -> pos.copy(x = pos.x - 1, y = pos.y + 1)
      West -> pos.copy(x = pos.x - 1)
      NorthWest -> pos.copy(x = pos.x - 1, y = pos.y - 1)
    }
  }

  fun turnRight(): Direction {
    return when (this) {
      North -> East
      NorthEast -> SouthEast
      East -> South
      SouthEast -> SouthWest
      South -> West
      SouthWest -> NorthWest
      West -> North
      NorthWest -> NorthEast
    }
  }
  companion object {
    val NorthEastSouthWest = setOf(North, East, South, West)
  }
}

fun Coordinate.allNeighbours(): Set<Coordinate> = Direction.values().map { it.move(this) }.toSet()