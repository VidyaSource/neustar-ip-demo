#Neustar IP Intelligence GeoPoint On-Demand (GPP) Demo Application

##Description

This application makes use of the Neustar IP Intelligence GeoPoint On-Demand (GPP) REST service to produce a simple
web interface displaying the location information that can be derived from the user's IP. The purpose of the application
is to demonstrate various approaches to different challenges in application development and to balance sound engineering
practices without submitting to impractical dogma.

##Technical Details

The application is built with [Gradle](http://www.gradle.org/) but uses the [Wrapper](http://www.gradle.org/docs/current/userguide/userguide_single.html#gradle_wrapper),
so you not have to install Gradle. Because of the
Java requirement, this is a Spring MVC application with a JSP front end utilizing [Twitter Bootstrap](http://getbootstrap.com/)
via [WebJars](http://www.webjars.org/).
Other technologies include [Jackson](https://github.com/FasterXML/jackson-databind) for JSON serialization/deserialization,
[Logback](http://logback.qos.ch/) for logging, and of course core
Spring for dependency injection and REST client capability.

For testing, JUnit4 is used in concert with [EasyMock](http://www.easymock.org/) and Spring's own unit and integration testing facilities. The Gradle
build file has a custom task to run integration tests (as denoted by an "IT" suffix by convention) separately from unit
tests. Conversely, and perhaps obviously, the built-in *test* task excludes those tests to only run the unit tests.

##Questions?

If you have questions, please check out the Vidya [website](http://www.vidyasource.com) or [E-mail](mailto:info@vidyasource.com) us.

Or you can find us pretty much everywhere:

* [Facebook](https://www.facebook.com/VidyaSource)
* [Twitter](https://twitter.com/VidyaSource)
* [YouTube](https://www.youtube.com/channel/UC24LVc8Bb65SF6LW-SLog9A)
* [LinkedIn](http://www.linkedin.com/company/3285099?trk=prof-exp-company-name)
* [Google+](https://plus.google.com/+Vidyasource)

We would even do Pintrest if we thought it would help.
