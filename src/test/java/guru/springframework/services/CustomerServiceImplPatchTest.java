package guru.springframework.services;


import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
public class CustomerServiceImplPatchTest {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	CustomerService customerService;
	
	@BeforeAll
	public void setUp() throws Exception {
		
		System.out.println("====== Loading customer data ======");
		System.out.println(customerRepository.findAll().size());
		
		// setup data for testing
		Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
		bootstrap.run(); // load data
		
		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
		
	}
	
	@Test
	public void patchCustomerUpdateFirstName() throws Exception {
		
		String updatedName = "updatedFirstName";
		Long id = getCustomerIdValue();
		
		Customer originalCustomer = customerRepository.getOne(id);
		assertNotNull(originalCustomer);
		
		String originalFirstName = originalCustomer.getFirstname();
		String originalLastName = originalCustomer.getLastname();
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(updatedName);
		
		customerService.patchCustomerByDTO(id, customerDTO);
		
		Customer updatedCustomer = customerRepository.findById(id).get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getFirstname());
		assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstname())));
		assertThat(originalLastName, (equalTo(updatedCustomer.getLastname())));
	}
	
	@Test
	public void patchCustomerUpdateLastName() throws Exception {
		
		String updatedName = "updatedLastName";
		Long id = getCustomerIdValue();
		
		Customer originalCustomer = customerRepository.getOne(id);
		assertNotNull(originalCustomer);
		
		String originalFirstName = originalCustomer.getFirstname();
		String originalLastName = originalCustomer.getLastname();
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setLastname(updatedName);
		
		customerService.patchCustomerByDTO(id, customerDTO);
		
		Customer updatedCustomer = customerRepository.findById(id).get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getLastname());
		assertThat(originalFirstName, (equalTo(updatedCustomer.getFirstname())));
		assertThat(originalLastName, not(equalTo(updatedCustomer.getLastname())));
	}
	
	private Long getCustomerIdValue() throws Exception {
		
		List<Customer> customers = customerRepository.findAll();
		
		System.out.println("Customer found: " + customers.size());
		
		// return first id
		return customers.get(0).getId();
	}
}
