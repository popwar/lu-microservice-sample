package org.lu.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.lu.data.Customer;
import org.lu.repository.CustomerRepository;
import org.lu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Set<Customer> getAllCustomers() {
		Set<Customer> customers = new HashSet<>();
		customerRepository.findAll().forEach(
				customer -> customers.add(customer));
		return customers;
	}

	@Override
	public Customer getOneCustomer(String id) {
		return customerRepository.findOne(id);
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

}
