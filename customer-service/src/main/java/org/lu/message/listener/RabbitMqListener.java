package org.lu.message.listener;

import org.apache.log4j.Logger;
import org.lu.data.Customer;
import org.lu.service.CustomerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class RabbitMqListener implements RabbitMqListnerInterface<Customer> {
	private static final Logger logger = Logger
			.getLogger(RabbitMqListener.class);

	@Autowired
	private CustomerService customerService;

	@RabbitListener(queues = { "customerQueue" })
	public void processQueue(Customer message) {
		logger.info("Received from queue with name:" + message.getUserName());
		customerService.saveCustomer(message);
	}
}
