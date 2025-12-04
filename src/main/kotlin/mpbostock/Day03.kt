package mpbostock

object Day03 {
  data class Battery(val joltage: Int)
  data class BatteryBank(val batteries: List<Battery>) {
    val highestJoltage: Int
      get() {
        val firstHighestJoltage = batteries.withIndex().take(batteries.size - 1).maxBy { it.value.joltage }
        val secondHighestJoltage = batteries.drop(firstHighestJoltage.index + 1).maxBy { it.joltage }
        return "${firstHighestJoltage.value.joltage}${secondHighestJoltage.joltage}".toInt()
      }

    val highestJoltageWithOverride: Long
      get() {
        return 1L //WIP
      }
    companion object {
      fun fromString(batteries: String): BatteryBank {
        return BatteryBank(batteries.map { Battery(it.digitToInt()) })
      }
    }
  }
  fun partOne(input: List<BatteryBank>): Int {
    return input.sumOf { it.highestJoltage }
  }

  fun partTwo(input: List<BatteryBank>): Long {
    return input.sumOf { it.highestJoltageWithOverride }
  }

  private val input = FileIO.readInput("day03input.txt") { s -> BatteryBank.fromString(s) }

  @JvmStatic
  fun main(args: Array<String>) {
    val partOneSolution = partOne(input)
    println(partOneSolution)

    val partTwoSolution = partTwo(input)
    println(partTwoSolution)
  }
}