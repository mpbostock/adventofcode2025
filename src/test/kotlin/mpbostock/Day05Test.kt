package mpbostock

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day05Test {
  val testData = listOf(
    "3-5",
    "10-14",
    "16-20",
    "12-18",
    "",
    "1",
    "5",
    "8",
    "11",
    "17",
    "32",
  )

  @Test
  fun `test part one`() {
    assertEquals(3, Day05.partOne(testData))
  }

  @Test
  fun `test part two`() {
    assertEquals(14L, Day05.partTwo(testData))
  }

}