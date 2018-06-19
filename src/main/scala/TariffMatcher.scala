import java.io.FileInputStream

import play.api.libs.json._

object TariffMatcher {

  def main(args: Array[String]): Unit = {
    println(readJson("./src/main/resources/copy_of_prices.json"))

  }

  def readJson(path: String) = {
    val stream = new FileInputStream(path)
    val json = try {  Json.parse(stream) } finally { stream.close() }
    json
  }
}

case class Tariff(Name: String,
                  RatePower: Double,
                  RateGas: Double,
                  StandingCharge: Double)