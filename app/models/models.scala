package models

import java.util.Date

case class WeatherDay(
  name: String, // today, tomorrow, memorial day
  date: Date,
  high: Int,
  low: Int,
  chanceOfPrecipitation: Double)

case class WeatherResult(
  zipcode: String,
  weatherDays: List[WeatherDay] )
