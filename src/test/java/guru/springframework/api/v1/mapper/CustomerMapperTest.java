package guru.springframework.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;

public class CustomerMapperTest {
    
    public final static String FIRST_NAME = "Nickolas";
    public final static String LAST_NAME = "Jonathan";
    public final static Long ID = 1L;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() throws Exception {

        // given
        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setId(ID);

        // when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        // then
        assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals(String.valueOf(FIRST_NAME), customerDTO.getFirstName());
        assertEquals(String.valueOf(LAST_NAME), customerDTO.getLastName());
        
    }

}
