package controllers

import play.api.mvc._
import services.WeatherClient

object Application extends Controller {
  val weatherClient = new WeatherClient()

  def index = Action {
    Redirect(routes.Application.daily("90210"))
  }

  def daily(zip: String) = Action {
    weatherClient.getWeatherForZipcode(zip) match {
      case Some(weatherResult) => Ok(views.html.daily(weatherResult))
      case None => Ok("couldn't get your data")
    }
  }
}