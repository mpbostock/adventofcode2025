package mpbostock

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day02Test {

  val testData = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"

  @Test
  fun `test part one`() {
    assertEquals(1227775554L, Day02.partOne(listOf(testData)))
  }

  @Test
  fun `test part two`() {
    assertEquals(4174379265L, Day02.partTwo(listOf(testData)))
  }

}