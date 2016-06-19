package org.lu.message.config;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MarshallingMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

//@Configuration
@EnableRabbit
public class RabbitConfiguration {
	private static final Logger LOGGER = Logger.getLogger(RabbitConfiguration.class);
	
	private static final MessageConverter CONVERTER = new Jackson2JsonMessageConverter();
	
	@PostConstruct
	public void start(){
		LOGGER.info("Rabbit is running");
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
				"localhost", 5672);
		return connectionFactory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	private MarshallingMessageConverter messageConverter() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan("org.lu.data");
		MarshallingMessageConverter messageConverter = new MarshallingMessageConverter(
				marshaller);
		messageConverter.setUnmarshaller(marshaller);
		return messageConverter;
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setMessageConverter(CONVERTER);
		return template;
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setConcurrentConsumers(3);
		factory.setMaxConcurrentConsumers(10);
		factory.setMessageConverter(CONVERTER);
		return factory;
	}

}
