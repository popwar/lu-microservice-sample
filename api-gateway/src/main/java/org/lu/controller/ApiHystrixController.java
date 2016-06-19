package org.lu.controller;

import java.util.Collections;
import java.util.Set;

import javax.validation.Valid;

import org.lu.client.CustomerClient;
import org.lu.client.ExpenseClient;
import org.lu.data.Customer;
import org.lu.data.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * circuit breaker used to warp the service calling
 * 
 * @author Lu
 *
 */
@Component
public class ApiHystrixController {

	@Autowired
	private CustomerClient customerClient;

	@Autowired
	private ExpenseClient expenseClient;

	@HystrixCommand(fallbackMethod = "getDefaultCustomers")
	public Set<Customer> getCustomers() {
		return customerClient.customers();
	}

	public Set<Customer> getDefaultCustomers() {
		return Collections.emptySet();
	}

	@HystrixCommand(fallbackMethod = "getDefaultCustomer")
	public Customer getCustomer(String id) {
		return customerClient.customer(id);
	}

	public Customer getDefaultCustomer(String id) {
		return new Customer();
	}

	@HystrixCommand(fallbackMethod = "updateDefaultCustomer")
	public Customer updateCustomer(String id, Customer customer) {
		return customerClient.updateCustomer(id, customer);
	}

	public Customer updateDefaultCustomer(String id, Customer customer) {
		return new Customer();
	}

	@HystrixCommand(fallbackMethod = "createDefaultCustomer")
	public void saveNewCustomer(Customer customer) {
		customerClient.saveNewCustomer(customer);
	}

	public void createDefaultCustomer(Customer customer) {
	}

	@HystrixCommand(fallbackMethod = "getDefaultgetExpense")
	public Set<Expense> getExpense() {
		return expenseClient.expenses();
	}

	public Set<Expense> getDefaultgetExpense() {
		return Collections.emptySet();
	}
}
