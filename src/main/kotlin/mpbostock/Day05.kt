package mpbostock

import kotlin.math.max
import kotlin.math.min

object Day05 {
  data class FreshIdRange(val range: LongRange) {
    companion object {
      fun fromString(range: String): FreshIdRange {
        val split = range.split("-")
        return FreshIdRange(split[0].toLong()..split[1].toLong())
      }
    }
  }

  data class KitchenInventory(val freshIdRanges: Set<FreshIdRange>, val availableIds: Set<Long>) {
    val freshAvailableIngredients: Int
      get() = availableIds.filter { id -> freshIdRanges.any { id in it.range } }.size

    val allFreshIngredientIds: Long
      get() {
        tailrec fun countFreshIngredientIds(
          freshIdRanges: Set<FreshIdRange>,
          minId: Long,
          maxId: Long,
          count: Long = 0
        ): Long {
          if (freshIdRanges.isEmpty()) {
            return count
          } else {
            val sortedFreshIdRanges = freshIdRanges.sortedBy { it.range.start }
            val freshIdRange = sortedFreshIdRanges.first().range
            val minIdToAdd = max(maxId + 1, freshIdRange.start)
            val maxIdToAdd = max(maxId, freshIdRange.endInclusive)
            return countFreshIngredientIds(
              sortedFreshIdRanges.drop(1).toSet(),
              min(freshIdRange.start, minId),
              maxIdToAdd,
              count + (maxIdToAdd - minIdToAdd + 1)
            )
          }
        }
        return countFreshIngredientIds(freshIdRanges, Long.MAX_VALUE, Long.MIN_VALUE)
      }

    companion object {
      fun fromInput(input: List<String>): KitchenInventory {
        val sections = input.joinToString("\n").split("\n\n")
        val freshIdRanges = sections.first().split("\n").map { FreshIdRange.fromString(it) }
        val availableIds = sections[1].split("\n").map { it.toLong() }
        return KitchenInventory(freshIdRanges.toSet(), availableIds.toSet())
      }
    }
  }

  fun partOne(input: List<String>): Int {
    return KitchenInventory.fromInput(input).freshAvailableIngredients
  }

  fun partTwo(input: List<String>): Long {
    return KitchenInventory.fromInput(input).allFreshIngredientIds
  }

  private val input = FileIO.readInput("day05input.txt") { s -> s }

  @JvmStatic
  fun main(args: Array<String>) {
    val partOneSolution = partOne(input)
    println(partOneSolution)

    val partTwoSolution = partTwo(input)
    println(partTwoSolution)
  }
}