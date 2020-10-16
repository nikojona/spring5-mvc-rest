package guru.springframework.services;

import java.util.List;

import guru.springframework.api.v1.model.CustomerDTO;

public interface CustomerService {
    
    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);
    
    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
    
    CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);
    
    CustomerDTO patchCustomerByDTO(Long id, CustomerDTO customerDTO);
    
}
