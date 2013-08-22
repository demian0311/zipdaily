package controllers

import play.api.mvc._
import com.codahale.metrics._
import services.WeatherClient
import java.util.concurrent.TimeUnit

object Application extends Controller {
  // create the metrics registry
  val metricRegistry = new MetricRegistry()

  val consoleReporter: ConsoleReporter = ConsoleReporter
    .forRegistry(metricRegistry)
    .convertRatesTo(TimeUnit.SECONDS)
    .convertDurationsTo(TimeUnit.MILLISECONDS)
    .build()
  consoleReporter.start(1, TimeUnit.MINUTES)

  val jmxReporter: JmxReporter = JmxReporter
    .forRegistry(metricRegistry)
    .convertRatesTo(TimeUnit.SECONDS)
    .convertDurationsTo(TimeUnit.MILLISECONDS)
    .build()
  jmxReporter.start()

  val weatherClient = new WeatherClient(metricRegistry)

  def index = Action {
    //Redirect(routes.Application.daily("90210"))
    Ok("add a zipcode to the URL")
  }

  def daily(zip: String) = Action {
    weatherClient.getWeatherForZipcode(zip) match {
      case Some(weatherResult) => Ok(views.html.daily(weatherResult))
      case None => Ok("couldn't get your data")
    }
  }
}