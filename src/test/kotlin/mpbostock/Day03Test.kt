package mpbostock

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day03Test {
  val testData = listOf(
    "987654321111111",
    "811111111111119",
    "234234234234278",
    "818181911112111"
  )
  @Test
  fun `test part one`() {
    assertEquals(357, Day03.partOne(testData.map { Day03.BatteryBank.fromString(it) }))
  }

  @Test
  fun `test part two`() {
    assertEquals(3121910778619L, Day03.partTwo(testData.map { Day03.BatteryBank.fromString(it) }))
  }

}