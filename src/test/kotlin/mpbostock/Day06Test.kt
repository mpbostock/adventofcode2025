package mpbostock

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day06Test {
  private val testData = listOf(
    "123 328  51 64 ",
    " 45 64  387 23 ",
    "  6 98  215 314",
    "*   +   *   +  "
  )

  @Test
  fun `test part one`() {
    assertEquals(4277556L, Day06.partOne(testData))
  }

  @Test
  fun `test part two`() {
    assertEquals(3263827L, Day06.partTwo(testData))
  }

}