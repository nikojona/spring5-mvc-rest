package guru.springframework.services;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;


import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@TestInstance(Lifecycle.PER_CLASS)
public class CustomerServiceImplTest {
    
    @Mock
    CustomerRepository customerRepository;
    
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    
    CustomerServiceImpl customerService;
    
    @BeforeAll
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerMapper, customerRepository);
    }
    
    @Test
    public void testGetAllCustomers() throws Exception {

        // given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname("Alex");
        customer1.setLastname("Young");
        
        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstname("Michael");
        customer2.setLastname("Jordan");
        
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));
        
        // when
        List<CustomerDTO> customerDTO = customerService.getAllCustomers();
        
        // then
        assertEquals(2, customerDTO.size());
    }

    @Test
    public void testGetCustomerById() throws Exception {

        // given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname("Hillary");
        customer1.setLastname("Clinton");

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));

        // when
        CustomerDTO customerDTO1 = customerService.getCustomerById(1L);

        assertEquals("Hillary", customerDTO1.getFirstname());
    }
    
    @Test
    public void testCreateNewCustomer() throws Exception {
    	
    	// given
    	CustomerDTO customerDTO = new CustomerDTO();
    	customerDTO.setFirstname("Lebrown");
    	
    	Customer savedCustomer = new Customer();
    	savedCustomer.setFirstname(customerDTO.getFirstname());
    	savedCustomer.setLastname(customerDTO.getLastname());
    	savedCustomer.setId(1L);
    	
    	when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
    	
    	// when
    	CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);
    	
    	// then
    	assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
    	assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
    	
    }
    
   @Test
   public void testSaveCustomerByDTO() throws Exception {
	   
    	// given
    	CustomerDTO customerDTO = new CustomerDTO();
    	customerDTO.setFirstname("Michael");
    	
    	Customer savedCustomer = new Customer();
    	savedCustomer.setFirstname(customerDTO.getFirstname());
    	savedCustomer.setLastname(customerDTO.getLastname());
    	savedCustomer.setId(1L);
    	
    	when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
    	
    	// when
    	CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO); 
    	
    	// then
    	assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
    	assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
   }
}
