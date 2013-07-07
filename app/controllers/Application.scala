package controllers

//import play.api._
import play.api.mvc._
import models.{WeatherDay, WeatherResult}
import java.util.Date
import services.WeatherClient

object Application extends Controller {

  val weatherClient = new WeatherClient()

  def index = Action {
    // TODO: maybe some IP to zip lookup?
    Redirect(routes.Application.daily("90210"))
  }

  def daily(zip: String) = Action {
    weatherClient.getWeatherForZipcode(zip) match {
      case Some(weatherResult) => Ok(views.html.daily(weatherResult))
      case None => Ok("couldn't get your data")
    }

    /*
    val weatherResult = WeatherResult(
      zipcode="70503",
      weatherDays = List(
        WeatherDay(
          name="today",
          date=new Date(),
          high=90,
          low=73,
          chanceOfPrecipitation = .20
        ),
        WeatherDay(
          name="tomorrow",
          date=new Date(),
          high=93,
          low=74,
          chanceOfPrecipitation = .10
        )
      )
    )
    */

  }
}