# lu-microservice-sample

using spring boot, spring cloud,spring netflix Eureka, spring netflix Feign, spring netflix hystrix,
spring security, spring security oauth2, rabbitmq, mongodb, and AngularJS to build this sample.

this sample has an api-gateway which is built by spring cloud, Eureka, Feign client, and hystrix circuit breaker 
and serves as eureka discovery service as well.

includes two rest services customer-service(return data from mongodb) and expense-service(return dummy data) 
and register them in the discovery service.

also has a web app developed by AngularJS and call all the api exposed by api-gateway.

use the example from https://github.com/spring-guides/tut-spring-security-and-angular-js/tree/master/oauth2
to implement the authenticate server that works for SSO

all the applications are based on spring boot.
