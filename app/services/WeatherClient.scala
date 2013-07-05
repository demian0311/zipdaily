package services

import models.WeatherResult
import play.api.libs.ws.WS._
import play.libs.F.Promise
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import play.libs.Json
import play.api.libs.ws.WS

//import play.libs.F.Promise

class WeatherClient {
  val endpoint = "http://graphical.weather.gov/xml/SOAP_server/ndfdXMLserver.php"


  def getLatLongFromZipCode(zipcode: String): Option[String] = {
    val xmlContent = <soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ndf="http://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl">
      <soapenv:Header/>
      <soapenv:Body>
        <ndf:LatLonListZipCode soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <zipCodeList xsi:type="dwml:zipCodeListType" xmlns:dwml="http://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd">{zipcode}</zipCodeList>
        </ndf:LatLonListZipCode>
      </soapenv:Body>
    </soapenv:Envelope>

    // begin: from book
    val query = """paperclip OR "paper clip""""
    val url = "http://search.twitter.com/search.json"
    val responseFuture = WS.url(endpoint).post(xmlContent.toString())

    val response = Await.result(responseFuture, 10 seconds)
    println("response.body: " + response.body)
    // TODO: now actually parse the results and send back

    Some("30.2153,-92.0295")
  }

  def getWeatherForZipcode(zipcode: String): Option[WeatherResult] ={
    None
  }

}


/*
wsdl: http://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl

endpoint: http://graphical.weather.gov/xml/SOAP_server/ndfdXMLserver.php

### First get the lat, long info on a zip code

=== request ===
<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ndf="http://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl">
   <soapenv:Header/>
   <soapenv:Body>
      <ndf:LatLonListZipCode soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
         <zipCodeList xsi:type="dwml:zipCodeListType" xmlns:dwml="http://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd">70503</zipCodeList>
      </ndf:LatLonListZipCode>
   </soapenv:Body>
</soapenv:Envelope>

=== response ===
<SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/">
   <SOAP-ENV:Body>
      <ns1:LatLonListZipCodeResponse xmlns:ns1="http://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl">
         <listLatLonOut xsi:type="xsd:string">&lt;?xml version='1.0'?>&lt;dwml version='1.0' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:noNamespaceSchemaLocation='http://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd'>&lt;latLonList>30.2153,-92.0295&lt;/latLonList>&lt;/dwml></listLatLonOut>
      </ns1:LatLonListZipCodeResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>

### Now get the forecast for the lat/ long

=== request ===
<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ndf="http://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl">
   <soapenv:Header/>
   <soapenv:Body>
      <ndf:NDFDgenByDayLatLonList soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
         <listLatLon xsi:type="dwml:listLatLonType" xmlns:dwml="http://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd">30.2153,-92.0295</listLatLon>
         <startDate xsi:type="xsd:date">2013-07-03</startDate>
         <numDays xsi:type="xsd:integer">2</numDays>
         <Unit xsi:type="dwml:unitType" xmlns:dwml="http://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd">e</Unit>
         <format xsi:type="dwml:formatType" xmlns:dwml="http://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd">24 hourly</format>
      </ndf:NDFDgenByDayLatLonList>
   </soapenv:Body>
</soapenv:Envelope>

=== response ===
<SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/">
   <SOAP-ENV:Body>
      <ns1:NDFDgenByDayLatLonListResponse xmlns:ns1="http://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl">
         <dwmlByDayOut xsi:type="xsd:string"><![CDATA[<?xml version="1.0"?>
<dwml version="1.0" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="http://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd">
  <head>
    <product srsName="WGS 1984" concise-name="dwmlByDay" operational-mode="official">
      <title>NOAA's National Weather Service Forecast by 24 Hour Period</title>
      <field>meteorological</field>
      <category>forecast</category>
      <creation-date refresh-frequency="PT1H">2013-07-03T20:57:09Z</creation-date>
    </product>
    <source>
      <more-information>http://graphical.weather.gov/xml/</more-information>
      <production-center>Meteorological Development Laboratory<sub-center>Product Generation Branch</sub-center></production-center>
      <disclaimer>http://www.nws.noaa.gov/disclaimer.html</disclaimer>
      <credit>http://www.weather.gov/</credit>
      <credit-logo>http://www.weather.gov/images/xml_logo.gif</credit-logo>
      <feedback>http://www.weather.gov/feedback.php</feedback>
    </source>
  </head>
  <data>
    <location>
      <location-key>point1</location-key>
      <point latitude="30.22" longitude="-92.03"/>
    </location>
    <moreWeatherInformation applicable-location="point1">http://forecast.weather.gov/MapClick.php?textField1=30.22&amp;textField2=-92.03</moreWeatherInformation>
    <time-layout time-coordinate="local" summarization="24hourly">
      <layout-key>k-p24h-n2-1</layout-key>
      <start-valid-time>2013-07-03T06:00:00-05:00</start-valid-time>
      <end-valid-time>2013-07-04T06:00:00-05:00</end-valid-time>
      <start-valid-time>2013-07-04T06:00:00-05:00</start-valid-time>
      <end-valid-time>2013-07-05T06:00:00-05:00</end-valid-time>
    </time-layout>
    <time-layout time-coordinate="local" summarization="12hourly">
      <layout-key>k-p12h-n4-2</layout-key>
      <start-valid-time>2013-07-03T06:00:00-05:00</start-valid-time>
      <end-valid-time>2013-07-03T18:00:00-05:00</end-valid-time>
      <start-valid-time>2013-07-03T18:00:00-05:00</start-valid-time>
      <end-valid-time>2013-07-04T06:00:00-05:00</end-valid-time>
      <start-valid-time>2013-07-04T06:00:00-05:00</start-valid-time>
      <end-valid-time>2013-07-04T18:00:00-05:00</end-valid-time>
      <start-valid-time>2013-07-04T18:00:00-05:00</start-valid-time>
      <end-valid-time>2013-07-05T06:00:00-05:00</end-valid-time>
    </time-layout>
    <time-layout time-coordinate="local" summarization="24hourly">
      <layout-key>k-p2d-n1-3</layout-key>
      <start-valid-time>2013-07-03T06:00:00-05:00</start-valid-time>
      <end-valid-time>2013-07-05T06:00:00-05:00</end-valid-time>
    </time-layout>
    <parameters applicable-location="point1">
      <temperature type="maximum" units="Fahrenheit" time-layout="k-p24h-n2-1">
        <name>Daily Maximum Temperature</name>
        <value>89</value>
        <value>90</value>
      </temperature>
      <temperature type="minimum" units="Fahrenheit" time-layout="k-p24h-n2-1">
        <name>Daily Minimum Temperature</name>
        <value>70</value>
        <value>71</value>
      </temperature>
      <probability-of-precipitation type="12 hour" units="percent" time-layout="k-p12h-n4-2">
        <name>12 Hourly Probability of Precipitation</name>
        <value>27</value>
        <value>11</value>
        <value>19</value>
        <value>16</value>
      </probability-of-precipitation>
      <hazards time-layout="k-p2d-n1-3">
        <name>Watches, Warnings, and Advisories</name>
        <hazard-conditions xsi:nil="true"/>
      </hazards>
      <weather time-layout="k-p24h-n2-1">
        <name>Weather Type, Coverage, and Intensity</name>
        <weather-conditions weather-summary="Chance Thunderstorms">
          <value coverage="chance" intensity="none" weather-type="thunderstorms" qualifier="none"/>
          <value coverage="chance" intensity="light" additive="and" weather-type="rain showers" qualifier="none"/>
        </weather-conditions>
        <weather-conditions weather-summary="Slight Chance Thunderstorms">
          <value coverage="slight chance" intensity="none" weather-type="thunderstorms" qualifier="none"/>
          <value coverage="slight chance" intensity="light" additive="and" weather-type="rain showers" qualifier="none"/>
        </weather-conditions>
      </weather>
      <conditions-icon type="forecast-NWS" time-layout="k-p24h-n2-1">
        <name>Conditions Icons</name>
        <icon-link>http://www.nws.noaa.gov/weather/images/fcicons/scttsra30.jpg</icon-link>
        <icon-link>http://www.nws.noaa.gov/weather/images/fcicons/scttsra20.jpg</icon-link>
      </conditions-icon>
    </parameters>
  </data>
</dwml>]]></dwmlByDayOut>
      </ns1:NDFDgenByDayLatLonListResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
 */
