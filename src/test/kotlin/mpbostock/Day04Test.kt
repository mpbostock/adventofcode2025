package mpbostock

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day04Test {
  val testData = listOf(
    "..@@.@@@@.",
    "@@@.@.@.@@",
    "@@@@@.@.@@",
    "@.@@@@..@.",
    "@@.@@@@.@@",
    ".@@@@@@@.@",
    ".@.@.@.@@@",
    "@.@@@.@@@@",
    ".@@@@@@@@.",
    "@.@.@@@.@.",
  )

  @Test
  fun `test part one`() {
    assertEquals(13, Day04.partOne(testData))
  }

  @Test
  fun `test part two`() {
    assertEquals(43, Day04.partTwo(testData))
  }

}