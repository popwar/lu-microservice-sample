package org.lu.client;

import java.util.Set;

import org.lu.data.Expense;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("expense-service")
public interface ExpenseClient {

	@RequestMapping(value = "/expenses", method = RequestMethod.GET)
	public Set<Expense> expenses();
}
