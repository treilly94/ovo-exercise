import org.scalatest.{FlatSpec, Matchers}

class TariffMatcherTest extends FlatSpec with Matchers {

  "testMain" should "return the cost of gas and electricity in order" in {
    TariffMatcher.main(Array("cost", "2000", "2300"))
  }
  it should "return the amount of power used annually from a monthly spend" in {
    TariffMatcher.main(Array("greener-energy", "power", "40"))
  }
  it should "return the amount of gas used annually from a monthly spend" in {
    TariffMatcher.main(Array("better-energy", "gas", "25"))
  }


  "testReadJson" should "return a list of Tariffs" in {

  }

}
