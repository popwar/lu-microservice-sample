package org.lu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
//import org.springframework.cloud.netflix.ribbon.RibbonClient;
//import org.lu.loadbalancer.RibbonConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients 
//@RibbonClient(name = "customer-service", configuration = RibbonConfiguration.class)
@EnableCircuitBreaker
@ComponentScan({"org.lu.controller", "org.lu.client"})
public class ApiCallerApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "apicaller-service");
		SpringApplication.run(ApiCallerApplication.class, args);
	}

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
