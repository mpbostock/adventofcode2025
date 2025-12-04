package mpbostock

object Day04 {
  class ForkliftMap(grid: Grid<Char>): Grid<Char> by grid {
    private val rollsOfPaper = coordinatesOfInterest

    val accessibleRollsOfPaper: Int
      get() {
        return findAccessibleRollsOfPaper(rollsOfPaper).size
      }

    val totalRollsRemoved: Int
      get() {
        tailrec fun removeAccessibleRolls(rollsOfPaper: Set<Coordinate>, numRollsRemoved: Int = 0): Int {
          val accessibleRolls = findAccessibleRollsOfPaper(rollsOfPaper)
          return if (accessibleRolls.isEmpty()) {
            print { coord ->
              if (coord in rollsOfPaper) '*' else ' '
            }
            numRollsRemoved
          } else {
            removeAccessibleRolls(
              rollsOfPaper - accessibleRolls,
              numRollsRemoved + accessibleRolls.size
            )
          }
        }
        return removeAccessibleRolls(rollsOfPaper)
      }

    private fun findAccessibleRollsOfPaper(rollsOfPaper: Set<Coordinate>): Set<Coordinate> {
      return rollsOfPaper.filter { rollOfPaper ->
        rollOfPaper.allNeighbours().count { it in rollsOfPaper } < 4
      }.toSet()
    }

    companion object {
      fun fromInput(input: List<String>): ForkliftMap =
        ForkliftMap(Grid.fromInput(input, '.', { c -> c }) { c, _ -> c == '@' })
    }
  }

  fun partOne(input: List<String>): Int {
    return ForkliftMap.fromInput(input).accessibleRollsOfPaper
  }

  fun partTwo(input: List<String>): Int {
    return ForkliftMap.fromInput(input).totalRollsRemoved
  }

  private val input = FileIO.readInput("day04input.txt") { s -> s }

  @JvmStatic
  fun main(args: Array<String>) {
    val partOneSolution = partOne(input)
    println(partOneSolution)

    val partTwoSolution = partTwo(input)
    println(partTwoSolution)
  }
}