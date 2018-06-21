package com.example

import java.io.FileInputStream

import play.api.libs.functional.syntax._
import play.api.libs.json._

object TariffMatcher {

  val vat: Double = 0.05
  val vatMultiplier: Double = vat + 1
  val decimalPlaces: String = "%1.2f"

  /*
      A method that calculates an annual cost inclusive of VAT for applicable tariffs, sorted by cheapest first.
   */
  def calculateCost(tariffs: List[Tariff], pUsage: Double, gUsage: Double): Map[String, String] = {
    // Remove tariffs that don't supply power/gas when needed
    val newTariffs = (pUsage, gUsage) match {
      case (p, g) if p > 0.0 && g > 0.0 => tariffs.filter(t => t.RatePower.isDefined && t.RateGas.isDefined)
      case (p, g) if p > 0.0 => tariffs.filter(t => t.RatePower.isDefined)
      case (p, g) if g > 0.0 => tariffs.filter(t => t.RateGas.isDefined)
      case _ => throw new IllegalArgumentException("pUsage and gUasge cant both be zero")
    }

    newTariffs
      .map(t =>
        t.Name -> decimalPlaces.format( // Map names to two decimal place formatted numbers
          (
            (t.RatePower.getOrElse(0.0) * pUsage) +
              (t.RateGas.getOrElse(0.0) * gUsage) +
              (t.StandingCharge.getOrElse(0.0) * 12)
            ) * vatMultiplier
        )
      )
      .sortBy(_._2) // Sort by the values
      .toMap // Convert to a map
  }

  /*
    Calculates how much energy (in kWh) would be used annually from a monthly spend in pounds
   */
  def calculateUsage(tariffs: List[Tariff], name: String, fuel: String, target: Double): String = {
    // Get tariff details
    val tariff: Tariff = tariffs.filter(t => t.Name == name).head
    // Get price of fuel
    val fuelPrice: Double = fuel match {
      case "power" => tariff.RatePower.get
      case "gas" => tariff.RateGas.get
      case _ => throw new IllegalArgumentException("Fuel does not exist")
    }
    // Calculate price and format output
    decimalPlaces.format(
      (((target - target * vat) - tariff.StandingCharge.get) / fuelPrice) * 12
    )
  }

  /*
    Reads the tariffs from the json file into the Tariff case class
   */
  def readJson(path: String): List[Tariff] = {

    // Define how to read the json into the Tariff case class
    implicit val tariffReads: Reads[Tariff] = (
      (__ \ "tariff").read[String] and
        (__ \ "rates" \ "power").readNullable[Double] and
        (__ \ "rates" \ "gas").readNullable[Double] and
        (__ \ "standing_charge").readNullable[Double]
      ) (Tariff)

    // Stream the json from the file into the parser
    val stream = new FileInputStream(path)
    val json = try {
      Json.parse(stream)
    } finally {
      stream.close()
    }
    // TODO Add in more error handling
    // Convert the json to a list of Tariffs
    try {
      json.validate[List[Tariff]].get
    }
  }
}
