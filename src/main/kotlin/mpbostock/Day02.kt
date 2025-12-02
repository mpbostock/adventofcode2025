package mpbostock

object Day02 {
  data class ProductIdRange(val range: LongRange) {
    val invalidIds: List<Long>
      get() = range.map { it.toString() }.filter { it.length % 2 == 0 }.filter {
        val half = it.length / 2
        val firstHalf = it.substring(0, half)
        val secondHalf = it.substring(half)
        firstHalf == secondHalf
      }.map { it.toLong() }

    val invalidIdsSequences: List<Long>
      get() {
        return range.map { it.toString() }.filter {
          val half = it.length / 2
          (1 .. half).any { i ->
            it.take(i).repeat(it.length / i) == it
          }
        }.map { it.toLong() }

      }
    companion object {
      fun fromString(range: String): ProductIdRange {
        val split = range.split("-")
        return ProductIdRange(split[0].toLong() .. split[1].toLong())
      }
    }
  }
  fun partOne(input: List<String>): Long {
    val productIdRanges = input[0].split(",").map { ProductIdRange.fromString(it) }
    return productIdRanges.sumOf { it.invalidIds.sum() }
  }

  fun partTwo(input: List<String>): Long {
    val productIdRanges = input[0].split(",").map { ProductIdRange.fromString(it) }
    return productIdRanges.sumOf { it.invalidIdsSequences.sum() }
  }

  private val input = FileIO.readInput("day02input.txt") { s -> s }

  @JvmStatic
  fun main(args: Array<String>) {
    val partOneSolution = partOne(input)
    println(partOneSolution)

    val partTwoSolution = partTwo(input)
    println(partTwoSolution)
  }
}