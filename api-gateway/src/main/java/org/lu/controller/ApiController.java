package org.lu.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.lu.client.CustomerClient;
import org.lu.data.Customer;
import org.lu.data.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * centralized api router, call customer api through resttemplate and eureka
 * serviceID
 * 
 * @author Lu
 *
 */
@RestController
public class ApiController {
	public static final String CUSTOMER_SERVICE_URL = "http://CUSTOMER-SERVICE";

	@LoadBalanced
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ApiHystrixController apiHytrixController;

	@RequestMapping(value = "/checkHealth", method = RequestMethod.GET)
	public String health() {
		return restTemplate.getForObject(CUSTOMER_SERVICE_URL + "/healthCheck",
				String.class);
	}

	/**
	 * get all customers by resttemplate and eureka serviceID
	 * 
	 * @return
	 */
	// @RequestMapping(value = "/customers", method = RequestMethod.GET,
	// produces = "application/json")
	public Set<Customer> customers() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);

		return restTemplate.exchange(CUSTOMER_SERVICE_URL + "/customers",
				HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<Set<Customer>>() {
				}).getBody();
	}

	/**
	 * get all customers by Feign and Hystrix
	 * 
	 * @return
	 */
	@RequestMapping(value = "/customers", method = RequestMethod.GET, produces = "application/json")
	public Set<Customer> getCustomers() {
		long start = System.currentTimeMillis();
		CompletableFuture<Set<Customer>> result = CompletableFuture.supplyAsync(
				apiHytrixController::getCustomers).thenCombineAsync(
				CompletableFuture.supplyAsync(apiHytrixController::getExpense),
				(customers, expenses) -> {
					for (Customer customer : customers) {
						for (Expense expense : expenses) {
							if (customer.getUserName().equalsIgnoreCase(
									expense.getUserName())) {
								customer.setExpense(expense.getValue());
								break;
							}
						}
					}
					long end = System.currentTimeMillis();
					System.out.println((end - start) + " ms");
					return customers;
				});
		try {
			return result.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return Collections.emptySet();
		}
	}

	/**
	 * get a customer by Feign and Hystrix
	 * 
	 * @return
	 */
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.GET, produces = "application/json")
	public Customer getCustomer(@PathVariable String id) {
		return apiHytrixController.getCustomer(id);
	}

	/**
	 * update a customer by Feign and Hystrix
	 * 
	 * @param id
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public Customer updateCustomer(@PathVariable String id,
			@Valid @RequestBody Customer customer) {
		return apiHytrixController.updateCustomer(id, customer);
	}

	@RequestMapping(value = "/customers", method = RequestMethod.POST, consumes = "application/json")
	public void saveNewCustomer(@Valid @RequestBody Customer customer) {
		apiHytrixController.saveNewCustomer(customer);
	}
}
