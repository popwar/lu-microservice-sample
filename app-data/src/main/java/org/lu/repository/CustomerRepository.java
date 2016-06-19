package org.lu.repository;

import org.lu.data.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends
		PagingAndSortingRepository<Customer, String> {

	Customer findByUserNameAndPassword(String userName, String password);
}
