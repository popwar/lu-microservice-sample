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

Eureka server: http://strl099021.mso.net:8761

Config server: http://strl099021.mso.net:8888, if you want to check the config files pulled from pwc github. please use http://strl099021.mso.net:8888/{servcieID}/default. For example, http://strl099021.mso.net:8888/notification-service/default displays the content of config files that can be applied by notification-service.

When configuring microservice, the spring.application.name should be the same as the {serviceID} mentioned above. Use http://eureka:8761/eureka/ to talk to eureka server. Use serviceId: config-service to talk to config server.

Every time after finish a specific microservice, the docker-compose.yml needs to be updated as well.

Before doing mvn package or mvn deploy, please login to dtr.artifacts-stg.pwc.com at the first place by using 'docker login dtr.artifacts-stg.pwc.com'. Otherwise you cannot build the docker image with plugin successfully.

Currently, we use docker 1.13 to simplify the process to group docker compose and docker swarm together. Use "docker stack deploy -c " to deploy services in the swarm env. In your local env, you might need to use "docker swarm init" to create a swarm manager node as the prerequisite.

Configure a new client service to consume microservices

In pom.xml, add spring cloud and eureka dependency

<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.2.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Camden.SR6</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
</dependencyManagement>
<dependency>
	  <groupId>org.springframework.boot</groupId>
	  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<!--feign client to simplify complex rest call-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-feign</artifactId>
</dependency>
<!--circuit breaker-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-hystrix</artifactId>
</dependency>
In main application class, include @SpringBootApplication, @EnableOAuth2Client, and @EnableFeignClients(optional)

Call APIs provided by microservice infrastructure in a back end client application

All the client requests go through API gateway. The current API address is http://strl099046:9999 and http://strl099047:9999

Add Oauth2 config class for example:
@Configuration
public class OAuthConfig {
        
	//Register a interceptor that can handle token when using feign client
	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor() {
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), getClientCredentialDetail());
	}

        //Register an OAuth2RestTemplate that can handle token
	@Bean
	public OAuth2RestTemplate oAuthRestTemplate() {
		ClientCredentialsResourceDetails resourceDetails = getClientCredentialDetail();

		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, new DefaultOAuth2ClientContext());

		return restTemplate;
	}
	
	//Information that helps to get token from auth server
	private ClientCredentialsResourceDetails getClientCredentialDetail() {
		ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
		details.setAccessTokenUri("http://ApiGatewayHostname:9999/uaa/oauth/token");
		details.setClientSecret("0940f4890ca1343cq1j07");
		details.setClientId("common-service");
		details.setGrantType("client_credentials");
		details.setScope(Collections.singletonList("operation"));
		
		return details;
	}
}
accessTokenUri, clientId, clientSecret and grantType need to be repalced by the value configured in the properties or yaml file

2.Access the APIs from resouce server

Use OAuth2RestTemplate In your controller class
@Autowired
private OAuth2RestTemplate restTemplate;

restTemplate.postForEntity("http://ApiGatewayHostname:9999/{service-entry}/{path}/...", request, ...);
ApiGatewayHostname is the address or domain that represents the entry of API Gateway

Use Feign Client
Add a FeignClient for a specific microservice

@FeignClient(name = "serviceName", url="http://ApiGatewayHostname:9999/{service-entry}"))
public interface SomeClient {
      
    @RequestMapping(value = "/{path}", method = RequestMethod.POST)
    public Object doSomething(@RequestHeader String someHeader);
}
Name can be random, but must be specified. When not using eureka or static server list, url must be specified. The method signature defined in the FeignClient must be the same as the API endpoint that is provieded by the specific microservice. Use @RequestHeader in your method signature to add the customized header.

In your controller or service class

@Autowired
private SomeClient someClient;
  
someClient.doSomething();
Add Hystrix circuit breaker with Feign Client
Add @EnableCircuitBreaker to your main application. In your controller or service class, add @HystrixCommand(fallbackMethod = "fallbackMethod") to your method that uses Feign Client. "fallbackMethod" is the method that provide some content when all the microservice instances have been offline. You can also refer to https://github.com/Netflix/Hystrix/wiki/Configuration#execution.isolation.strategy to decide your own strategy
