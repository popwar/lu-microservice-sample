# lu-microservice-sample

using spring boot, spring cloud,spring netflix Eureka, spring netflix Feign, spring netflix hystrix,
spring security, spring security oauth2, rabbitmq, mongodb, and AngularJS to build this sample.

this sample has an api-gateway which is built by spring cloud, Eureka serveing as eureka discovery service, Feign client to simplify calling api within the environment(using service name registered with discovery service) instead of using resttemplate, 
Ribbon client side load balancer with default setup, and hystrix circuit breaker.

includes two rest services customer-service(return data from mongodb) and expense-service(return dummy data) 
and register them with the discovery service. Both services can deploy multipule instances(replica) in the environment 
for the client side load balancer and failover.

also has a web app developed by AngularJS and calls all the apis exposed by api-gateway.

use the example from https://github.com/spring-guides/tut-spring-security-and-angular-js/tree/master/oauth2
to implement the authenticate server that works for SSO

all the applications are based on spring boot.
