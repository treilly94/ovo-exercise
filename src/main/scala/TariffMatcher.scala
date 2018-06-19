import java.io.FileInputStream

import play.api.libs.functional.syntax._
import play.api.libs.json._


object TariffMatcher {
  def main(args: Array[String]): Unit = {
    val tariffs = readJson("./src/main/resources/copy_of_prices.json")
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
}

case class Tariff(Name: String,
                  RatePower: Option[Double],
                  RateGas: Option[Double],
                  StandingCharge: Option[Double])