import com.codahale.metrics.MetricRegistry
import models.{WeatherDay, WeatherResult}
import org.junit._
import services.WeatherClient
import org.junit.Assert._

/**
 * Err, these are not unit tests, integration tests for now.
 * The implementation code needs to tweaked to make that easier.
 */
class WeatherClientTest extends {

  val metricRegistry = new MetricRegistry()

  @Test
  def getLatLongFromZipCode(){
    val weatherClient: WeatherClient = new WeatherClient(metricRegistry)
    val result: Option[String] = weatherClient.getLatLongFromZipCode("61312")
    result match {
      case Some(value) => println("value: " + value)
      case None => {
        println("no value")
        fail("should get back a value")
      }
    }
  }

  @Test
  def getWeatherFromLatLong(){
    val weatherClient: WeatherClient = new WeatherClient(metricRegistry)
    val result: Option[List[WeatherDay]] = weatherClient.getWeatherFromLatLong("41.4712,-89.248")
    result match {
      case Some(value) => println("value: " + value)
      case None => {
        println("no value")
      }
    }
  }

  @Test
  def getWeatherFromZipcode_None_with_badZip(){
    val weatherClient: WeatherClient = new WeatherClient(metricRegistry)
    val result: Option[WeatherResult] = weatherClient.getWeatherForZipcode("61312")
    println("result " + result)
  }
}
