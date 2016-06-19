/*package org.lu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lu.data.Customer;
import org.lu.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(CustomerApplication.class)
@WebIntegrationTest
public class CustomerServiceTest {

	private static final String URL = "http://localhost:8081/customers";

	@Autowired
	CustomerRepository customerRepository;

	RestTemplate restTemplate = new TestRestTemplate();

	//@Test
	public void testFindAll() {//5746a455f292819c2d09b115
		System.out.println(restTemplate.getForObject(URL, String.class));
	}
	//@Test
	public void testFindOne(){
		System.out.println(customerRepository.findOne("5746a455f292819c2d09b115").getUserName());
	}
}*/
