package org.lu;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class ExpenseApplication {

	private static final Logger LOGGER = Logger
			.getLogger(ExpenseApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ExpenseApplication.class, args);
	}

	@RequestMapping(value = "/expenses", method = RequestMethod.GET)
	public Set<Expense> expenses() {
		LOGGER.info("------get all expense------");
		Set<Expense> set = new HashSet<>();
		Expense exp = new Expense("1", "Tom", 20.0);
		set.add(exp);
		return set;
	}

	private static class Expense {
		private final String id;

		private final String userName;

		private final Double value;

		private Expense(String id, String userName, Double value) {
			this.id = id;
			this.userName = userName;
			this.value = value;
		}

		public String getId() {
			return id;
		}

		public String getUserName() {
			return userName;
		}

		public Double getValue() {
			return value;
		}

	}
}
