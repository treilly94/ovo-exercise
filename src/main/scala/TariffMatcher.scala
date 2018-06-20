import java.io.FileInputStream

import play.api.libs.functional.syntax._
import play.api.libs.json._


object TariffMatcher {
  def main(args: Array[String]): Unit = {
    // Read Tariffs from file
    val tariffs = readJson("./src/main/resources/copy_of_prices.json")
    args.head match {
      case "cost" => calculateCost(tariffs, args(1).toDouble, args(2).toDouble) // for each print
      case "usage" => println(calculateUsage(tariffs, args(1), args(2), args(3).toDouble))
      // Errors
    }
  }

  def calculateCost(tariffs: List[Tariff], pUsage: Double, gUsage: Double): Map[String, String] = {
    tariffs
      .filter(t => t.RatePower.isDefined && pUsage > 0) // Remove tariffs that don't supply power when needed
      .filter(t => t.RateGas.isDefined && gUsage > 0) // Remove tariffs that don't supply gas when needed
      .map(t =>
        t.Name -> "%1.2f".format(     // Map names to two decimal place formatted numbers
          (t.RatePower.get * pUsage) + (t.RateGas.get * gUsage) + (t.StandingCharge.getOrElse(0.0) * 12)
        )
    )
      .sortBy(_._2) // Sort by the values
      .toMap // Convert to a map
  }

  def calculateUsage(tariffs: List[Tariff], name: String, fuel: String, target: Double): String = {
    // Get tariff details
    val tariff: Tariff = tariffs.filter(t => t.Name == name).head
    // Get price of fuel
    val fuelPrice: Double = if (fuel == "power") tariff.RatePower.get else tariff.RateGas.get
    // Calculate price and format output
    "%1.2f".format(((target - tariff.StandingCharge.get) / fuelPrice) * 12)
  }

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
    json.validate[List[Tariff]].get
  }

  val vat: Double = 0.05
}

case class Tariff(Name: String,
                  RatePower: Option[Double],
                  RateGas: Option[Double],
                  StandingCharge: Option[Double])