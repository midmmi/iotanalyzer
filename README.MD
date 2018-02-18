Application is built on a base of Spring Boot. It could be executed in server mode (without args) and in simulator mode 
(with --simulate arg). In simulate mode three different threads are sending random readings to the server every second. 

In simulate mode there is no Spring context, no error handling. I tried to keep it simple. 
Therefore it should be started after the server (simulation mode is not able to wait for the server) and all 
properties are hardcoded.

From architectural point of view application is classic three-layer app with presentation layer (controller), 
business logic (service) and database (in-memory H2).
 
There are two types of authorisation:
  - access by key (token) for registering real readings
  - basic auth with login and pass for querying against database

All credential info could be found in application.properties

In this implementation I used one database table for all various data from IoT, metric is a key for each type. 
Alternatively I could use solution where each metric has its own table. Here is a comparision with my approach:

Pros:
  - it will work much faster with big data amounts
  - it has higher limit of data capacity

Cons:
  - to add new metric (new IoT) we need to amend source code, rebuild, redeploy.
  - it will have more source code, which should be tested and maintained.

As this is not production application, I chose the simple way which solved the problem.

Application supports five query types: {Min, Max, Avg, Mdn, Cnt}
Mdn=median which is not implemented yet. Coming soon.
Cnt=count shows number of values under selected conditions.

There are two end points:
  - /query/{metric}/{query}/{from}/{to} to run a query against data where metric is a name of IoT, query is one of 
  QueryType and from, to are time interval boundaries in format "yyyy-MM-dd HH:mm:ss"
  - /store/{metric}/{value} to register real reading from {metric} device with {value} decimal value

To build application type from app-root dir (it uses maven)
./mvnw clean package  

To execute application in server mode type from the same dir
java -jar ./target/iotanalyzer-0.1.0.jar

To execute simulator type from the same dir
java -jar ./target/iotanalyzer-0.1.0.jar --simulate

Examples:
to perform query
curl -i --basic --user admin:123 "localhost:8080/query/Metric_3_/Cnt/2018-02-12%2020:22:33/2018-02-18%2020:22:33"

to register value:
curl -i -X POST -H X-Authority-Hash:4028843b619e9c5b01619e9c5be80000 --url localhost:8080/store/Metric_1_/333.4444