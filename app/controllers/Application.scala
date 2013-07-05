package controllers

import play.api._
import play.api.mvc._
import models.{WeatherDay, WeatherResult}
import java.util.Date

object Application extends Controller {


  def index = Action {
    // TODO: maybe some IP to zip lookup?
    Redirect(routes.Application.daily("90210"))
  }

  def daily(zip: String) = Action {
    val fakeWeatherResult = WeatherResult(
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

    Ok(views.html.daily(fakeWeatherResult))
  }
}
