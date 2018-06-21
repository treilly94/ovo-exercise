package com.example

import org.scalatest.{FlatSpec, Matchers}

class TariffMatcherTest extends FlatSpec with Matchers {

  val testTariffs: List[Tariff] = List(
    Tariff("better-energy", Some(0.1367), Some(0.0288), Some(8.33)),
    Tariff("2yr-fixed", Some(0.1397), Some(0.0296), Some(8.75)),
    Tariff("greener-energy", Some(0.1544), None, Some(8.33)),
    Tariff("simpler-energy", Some(0.1396), Some(0.0328), Some(8.75))
  )

  "calculateCost" should "return the cost of gas and power in order" in {
    val output: Map[String, String] = TariffMatcher.calculateCost(testTariffs, 2000.0, 2300.0)
    val expected: Map[String, String] = Map(
      "better-energy" -> "461.58",
      "2yr-fixed" -> "475.10",
      "simpler-energy" -> "482.62")

    output should be(expected)
  }
  it should "return the cost of just power in order" in {
    val output: Map[String, String] = TariffMatcher.calculateCost(testTariffs, 2000.0, 0.0)
    val expected: Map[String, String] = Map(
      "better-energy" -> "392.03",
      "simpler-energy" -> "403.41",
      "2yr-fixed" -> "403.62",
      "greener-energy" -> "429.20")

    output should be(expected)
  }
  it should "return the cost of just gas in order" in {
    val output: Map[String, String] = TariffMatcher.calculateCost(testTariffs, 0.0, 2000.0)
    val expected: Map[String, String] = Map(
      "better-energy" -> "165.44",
      "2yr-fixed" -> "172.41",
      "simpler-energy" -> "179.13")

    output should be(expected)
  }

  "calculateUsage" should "return the amount of power used annually from a monthly spend" in {
    val output: String = TariffMatcher.calculateUsage(testTariffs, "greener-energy", "power", 40.0)

    output should be("2305.96")
  }
  it should "return the amount of gas used annually from a monthly spend" in {
    val output: String = TariffMatcher.calculateUsage(testTariffs, "better-energy", "gas", 25.0)

    output should be("6425.00")
  }

  "readJson" should "return a list of Tariffs" in {
    val output: List[Tariff] = TariffMatcher.readJson("./src/test/resources/copy_of_prices.json")

    output should be(testTariffs)
  }

}
