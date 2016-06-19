package org.lu.service;

import java.util.Set;

import org.lu.data.Customer;

public interface CustomerService {
	Set<Customer> getAllCustomers();

	Customer getOneCustomer(String id);

	Customer saveCustomer(Customer customer);
}
