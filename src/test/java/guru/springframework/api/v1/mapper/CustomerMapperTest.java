package guru.springframework.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;

public class CustomerMapperTest {
    
    // public final static Long ID = 1L;
    public final static String FIRST_NAME = "Nickolas";
    public final static String LAST_NAME = "Jonathan";

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    
    @Test
    public void testCustomerToCustomerDtoIfNull() throws Exception {
    	// given
    	Customer customer = null;
    	
    	// when
        CustomerDTO customerDto = customerMapper.customerToCustomerDto(customer);
        assertNull(customerDto);
    }
    
    @Test
    public void testCustomerDtoToCustomerIfNull() throws Exception {
    	// given
    	CustomerDTO customerDto = null;
    	
    	// when
        Customer customer = customerMapper.customerDtoToCustomer(customerDto);
        assertNull(customer);
    }
    
    @Test
    public void testCustomerToCustomerDto() throws Exception {

        // given
        Customer customer = new Customer();
        // customer.setId(ID);
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        // when
        CustomerDTO customerDto = customerMapper.customerToCustomerDto(customer);

        // then
        // assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals(String.valueOf(FIRST_NAME), customerDto.getFirstname());
        assertEquals(String.valueOf(LAST_NAME), customerDto.getLastname());
        
    }
    
    @Test
    public void testCustomerDtoToCustomer() throws Exception {

        // given
        CustomerDTO customerDto = new CustomerDTO();
        // customer.setId(ID);
        customerDto.setFirstname(FIRST_NAME);
        customerDto.setLastname(LAST_NAME);

        // when
        Customer customer = customerMapper.customerDtoToCustomer(customerDto);

        // then
        // assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals(String.valueOf(FIRST_NAME), customer.getFirstname());
        assertEquals(String.valueOf(LAST_NAME), customer.getLastname());
        
    }

}
