## Zipdaily
This is just a small Play2 application that calls out to an 
external SOAP service to get information.

## Here's what I want to do next
- Integrate scuttle: libraryDependencies += "com.dadrox" % "scuttle" % "0.3"
- 1 library to wrap outgoing calls
- plug in caching
- plug in metrics
- plug in circuit breaker

## We use some government services to get the weather. 
There are some SOAPUI projects in the source but here are 
URLs for the services.

- http://www.nws.noaa.gov/ndfd/technical.htm
- http://www.nws.noaa.gov/ndfd/access_http.htm
- http://graphical.weather.gov/xml/
