package mpbostock

import kotlin.text.trim

object Day06 {
  enum class Operation(val operator: String) {
    ADD("+"), MULTIPLY("*");

    operator fun invoke(numbers: List<Long>): Long {
      return when (this) {
        ADD -> numbers.sum()
        MULTIPLY -> numbers.reduce { a, b -> a * b }
      }
    }

    companion object {
      fun fromString(operator: String): Operation = values().find { it.operator == operator }!!
    }
  }

  data class MathsProblem(val numbers: List<Long>, val operation: Operation) {
    val total = operation(numbers)
  }

  class MathsHomework(problems: List<MathsProblem>) {
    val grandTotal: Long = problems.sumOf { it.total }

    companion object {
      val simpleNumbersParser: (numbersColumns: List<List<String>>, operationsIndices: IntRange) -> List<List<Long>> = { numbersColumns, operationsIndices ->
        operationsIndices.map { i ->
          numbersColumns.map { it[i].trim().toLong() }
        }
      }

      val cephalopodsNumbersParser: (numbersColumns: List<List<String>>, operationsIndices: IntRange) -> List<List<Long>> = { numbersColumns, operationsIndices ->
        operationsIndices.map { i ->
          val numbersIndices = numbersColumns.first()[i].indices
          numbersIndices.map{ j -> numbersColumns.map { it[i][j] }.joinToString("").trim().toLong() }
        }
      }

      fun fromInput(input: List<String>, numbersParser: (numbersColumns: List<List<String>>, operationsIndices: IntRange) -> List<List<Long>> = simpleNumbersParser): MathsHomework {
        val operations = input.last().squashAndTrim().split(" ").map { Operation.fromString(it) }
        val numbersRows = input.dropLast(1)
        val columnIndices = input[0].indices.fold(emptySet<Int>()) { acc, i ->
          if (numbersRows.all { it[i] == ' ' } || i == input[0].length - 1) acc + i else acc
        }
        val numbersColumns = numbersRows.map {
          columnIndices.fold(0 to emptyList<String>()) { acc, i ->
            val col = if (i == columnIndices.last()) it.substring(acc.first) else it.substring(acc.first, i)
            i + 1 to acc.second + col
          }.second
        }
        val numbers = numbersParser(numbersColumns, operations.indices)
        return MathsHomework(numbers.zip(operations).map { MathsProblem(it.first, it.second) })
      }
    }
  }

  fun partOne(input: List<String>): Long {
    return MathsHomework.fromInput(input).grandTotal
  }

  fun partTwo(input: List<String>): Long  {
    return MathsHomework.fromInput(input, MathsHomework.cephalopodsNumbersParser).grandTotal
  }

  private val input = FileIO.readInput("day06input.txt") { s -> s }

  @JvmStatic
  fun main(args: Array<String>) {
    val partOneSolution = partOne(input)
    println(partOneSolution)

    val partTwoSolution = partTwo(input)
    println(partTwoSolution)
  }
}