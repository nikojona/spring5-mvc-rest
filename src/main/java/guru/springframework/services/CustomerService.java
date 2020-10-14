package guru.springframework.services;

import java.util.List;

import guru.springframework.api.v1.model.CustomerDTO;

public interface CustomerService {
    
    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerByFirstName(String firstName);
}
