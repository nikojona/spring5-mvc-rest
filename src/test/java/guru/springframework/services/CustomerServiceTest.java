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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@TestInstance(Lifecycle.PER_CLASS)
public class CustomerServiceTest {
    
    public static final Long ID = 2L;
    public static final String FIRST_NAME = "Daniel";
    public static final String LAST_NAME = "Ken";
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @BeforeAll
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }
    
    @Test
    public void testGetAllCustomers() throws Exception {

        // given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer(), new Customer());
        
        when(customerRepository.findAll()).thenReturn(customers);

        // when
        List<CustomerDTO> customerDTO = customerService.getAllCustomers();
        
        // then
        assertEquals(4, customerDTO.size());
    }

    @Test
    public void testGetCustomerById() throws Exception {

        // given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Hillary");
        customer1.setLastName("Clinton");

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));

        // when
        CustomerDTO customerDTO1 = customerService.getCustomerById(1L);

        assertEquals("Hillary", customerDTO1.getFirstName());
    }


    // @Test
    // public void testGetCustomerByFirstName() throws Exception {

    //     // given
    //     Customer customer = new Customer();
    //     customer.setId(ID);
    //     customer.setFirstName(FIRST_NAME);
    //     customer.setLastName(LAST_NAME);

    //     when(customerRepository.findByFirstName(anyString())).thenReturn(customer);

    //     // when
    //     CustomerDTO customerDto = customerService.getCustomerByFirstName(FIRST_NAME);

    //     // then
    //     assertEquals(ID, customerDto.getId());
    //     assertEquals(FIRST_NAME, customerDto.getFirstName());
    //     assertEquals(LAST_NAME, customerDto.getLastName());
    // }
}
