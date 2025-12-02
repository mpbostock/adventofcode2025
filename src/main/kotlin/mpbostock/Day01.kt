package mpbostock

object Day01 {
  fun partOne(input: List<DialRotation>): Int {
    val safeDial = input.fold(SafeDial()) { dial, rotation ->
      dial.rotate(rotation)
    }
    return safeDial.numZeroCrossings
  }

  fun partTwo(input: List<DialRotation>): Int {
    val safeDial = input.fold(SafeDial(countClickZeroCrossings = true)) { dial, rotation ->
      dial.rotate(rotation)
    }
    return safeDial.numZeroCrossings
  }

  enum class Direction {
    Left, Right
  }

  data class DialRotation(private val direction: Direction, internal val clicks: Int) {
    fun rotateFrom(position: Int): Int = when (direction) {
      Direction.Left -> position - clicks
      Direction.Right -> position + clicks
    }

    fun oneClick(): DialRotation = copy(clicks = 1)
    fun click(): DialRotation = copy(clicks = clicks - 1)

    companion object {
      fun fromString(input: String): DialRotation {
        val direction = when (input[0]) {
          'L' -> Direction.Left
          else -> Direction.Right
        }
        val amount = input.drop(1).toInt()
        return DialRotation(direction, amount)
      }
    }
  }

  data class SafeDial(
    private val position: Int = 50,
    val numZeroCrossings: Int = 0,
    private val countClickZeroCrossings: Boolean = false
  ) {
    fun rotate(rotation: DialRotation): SafeDial {
      val newPosition = calculatePosition(position, rotation)
      val newNumZeroCrossings =
        if (countClickZeroCrossings) {
          countClickZeroCrossings(position, rotation)
        } else {
          if (newPosition == 0) 1 else 0
        }
      return copy(newPosition, numZeroCrossings + newNumZeroCrossings)
    }

    private fun calculatePosition(position: Int, rotation: DialRotation): Int {
      val remainder = rotation.rotateFrom(position) % 100
      val newPosition = if (remainder < 0) 100 + remainder else remainder
      return newPosition
    }

    private fun countClickZeroCrossings(position: Int, rotation: DialRotation): Int {
      val oneClick = rotation.oneClick()
      tailrec fun countZeroCrossings(position: Int, rotation: DialRotation, count: Int): Int {
        if (rotation.clicks == 0) {
          return count
        }
        val newPosition = calculatePosition(position, oneClick)
        return countZeroCrossings(newPosition, rotation.click(), if (newPosition == 0) count + 1 else count)
      }
      return countZeroCrossings(position, rotation, 0)
    }
  }

  private val input = FileIO.readInput("day01input.txt") { s -> DialRotation.fromString(s) }

  @JvmStatic
  fun main(args: Array<String>) {
    val partOneSolution = partOne(input)
    println(partOneSolution)

    val partTwoSolution = partTwo(input)
    println(partTwoSolution)
  }
}