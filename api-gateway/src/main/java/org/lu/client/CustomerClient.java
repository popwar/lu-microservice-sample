package org.lu.client;

import java.util.Set;

import javax.validation.Valid;

import org.lu.data.Customer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * feign client for customer service
 * 
 * @author Lu
 *
 */
@FeignClient("customer-service")
public interface CustomerClient {

	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public Set<Customer> customers();

	@RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
	public Customer customer(@PathVariable("id") String id);

	@RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public Customer updateCustomer(@PathVariable("id") String id,
			@Valid @RequestBody Customer customer);

	@RequestMapping(value = "/customers", method = RequestMethod.POST, consumes = "application/json")
	public void saveNewCustomer(@Valid @RequestBody Customer customer);
}
