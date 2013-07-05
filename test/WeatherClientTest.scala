import org.junit._
import services.WeatherClient

class WeatherClientTest {
  val weatherClient: WeatherClient = new WeatherClient()

  @Test
  def getLatLongFromZipCode(){
    val result: Option[String] = weatherClient.getLatLongFromZipCode("61312")
    result match {
      case Some(value) => println("value: " + value)
      case None => println("no value")
    }
  }

  //@Ignore @Test
  //def getWeatherFromZipcode_None_with_badZip(){
  //  val result = weatherClient.getWeatherForZipcode("AAAAA")
  //  println("result " + result)
  //}
}
