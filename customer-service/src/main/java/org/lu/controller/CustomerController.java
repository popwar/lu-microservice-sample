package org.lu.controller;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.lu.data.Customer;
import org.lu.service.CustomerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
public class CustomerController {
	
	private static final Logger LOGGER = Logger.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@CrossOrigin
	@RequestMapping("/healthCheck")
	public String healthCheck() {
		return "Customer service is running";
	}

	@CrossOrigin
	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public Set<Customer> customers() {
		LOGGER.info("------get all customers------");
		return customerService.getAllCustomers();
	}

	@CrossOrigin
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
	public Customer customer(@PathVariable String id) {
		LOGGER.info("------get a customer------");
		return customerService.getOneCustomer(id);
	}

	@CrossOrigin
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public Customer updateCustomer(@PathVariable String id,
			@Valid @RequestBody Customer customer) {
		LOGGER.info("------update a customer------");
		customer.setId(id);
		return customerService.saveCustomer(customer);
	}

	@CrossOrigin
	@RequestMapping(value = "/customers", method = RequestMethod.POST, consumes = "application/json")
	public void saveNewCustomer(@Valid @RequestBody Customer customer) {
		LOGGER.info("------create a customers------");
		rabbitTemplate.convertAndSend("amq.direct", "newCustomer", customer);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		StringBuffer sb = new StringBuffer();
		fieldErrors.forEach(element -> sb.append(element.getField()
				+ element.getDefaultMessage()));

		return sb.toString();
	}
}
