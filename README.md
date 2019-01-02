A proof of concept implementation to add context information (e.g. trace id, country code) to log statements in akk-http, akka actors, scala futures based web application. The implemntation uses kamon tracing to achieve the objective

### How to run
- Download the project
- sbt run (starts web server on port 8080)
- http://localhost:8080/loggingcontext/api/de/test where de is alpha-2 country code
- observe logs