package mpbostock

import mpbostock.Day01.DialRotation
import mpbostock.Day01.Direction.Left
import mpbostock.Day01.Direction.Right
import mpbostock.Day01.SafeDial
import mpbostock.Day01.partOne
import mpbostock.Day01.partTwo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day01Test {
  val testData = listOf(
    "L68",
    "L30",
    "R48",
    "L5",
    "R60",
    "L55",
    "L1",
    "L99",
    "R14",
    "L82"
  )

  @Test
  fun `test part one`() {
    assertEquals(3, partOne(testData.map { DialRotation.fromString(it) }))
  }

  @Test
  fun `test part two`() {
    assertEquals(6, partTwo(testData.map { DialRotation.fromString(it) }))
  }

  @Test
  fun `rotating left less than start doesn't have a zero crossing`() {
    val dial = SafeDial(countClickZeroCrossings = true).rotate(DialRotation(Left, 49))
    assertEquals(0, dial.numZeroCrossings)
  }

  @Test
  fun `rotating left equal to start has one zero crossing`() {
    val dial = SafeDial(countClickZeroCrossings = true).rotate(DialRotation(Left, 50))
    assertEquals(1, dial.numZeroCrossings)
  }

  @Test
  fun `rotating left one full turn and less than start has one zero crossing`() {
    val dial = SafeDial(countClickZeroCrossings = true).rotate(DialRotation(Left, 149))
    assertEquals(1, dial.numZeroCrossings)
  }

  @Test
  fun `rotating left one full turn and equal to start has two zero crossings`() {
    val dial = SafeDial(countClickZeroCrossings = true).rotate(DialRotation(Left, 150))
    assertEquals(2, dial.numZeroCrossings)
  }

  @Test
  fun `rotating left crossing 0 then landing on zero has two zero crossings`() {
    val dial = SafeDial(countClickZeroCrossings = true).rotate(DialRotation(Left, 51)).rotate(DialRotation(Left, 99))
    assertEquals(2, dial.numZeroCrossings)
  }

  @Test
  fun `rotating left crossing 0 then rotating right crossing 0 has two zero crossings`() {
    val dial = SafeDial(countClickZeroCrossings = true).rotate(DialRotation(Left, 51)).rotate(DialRotation(Right, 2))
    assertEquals(2, dial.numZeroCrossings)
  }

  @Test
  fun `rotating left by 1000 has ten zero crossings`() {
    val dial = SafeDial(countClickZeroCrossings = true).rotate(DialRotation(Left, 1000))
    assertEquals(10, dial.numZeroCrossings)
  }

  @Test
  fun `rotating left from zero two full rotations has two zero crossings`() {
    val dial = SafeDial(position = 0, countClickZeroCrossings = true).rotate(DialRotation(Left, 200))
    assertEquals(2, dial.numZeroCrossings)
  }

  @Test
  fun `rotating right less than start doesn't have a zero crossing`() {
    val dial = SafeDial(countClickZeroCrossings = true).rotate(DialRotation(Right, 49))
    assertEquals(0, dial.numZeroCrossings)
  }

  @Test
  fun `rotating right equal to start has one zero crossing`() {
    val dial = SafeDial(countClickZeroCrossings = true).rotate(DialRotation(Right, 50))
    assertEquals(1, dial.numZeroCrossings)
  }

  @Test
  fun `rotating right one full turn and less than start has one zero crossing`() {
    val dial = SafeDial(countClickZeroCrossings = true).rotate(DialRotation(Right, 149))
    assertEquals(1, dial.numZeroCrossings)
  }

  @Test
  fun `rotating right one full turn and equal to start has two zero crossings`() {
    val dial = SafeDial(countClickZeroCrossings = true).rotate(DialRotation(Right, 150))
    assertEquals(2, dial.numZeroCrossings)
  }

}