package guru.springframework.services;

import java.util.List;
import java.util.stream.Collectors;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.repositories.CustomerRepository;

public class CustomerServiceImpl implements CustomerService {
    
    public final CustomerMapper customerMapper;
    public final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }
    
    @Override
    public List<CustomerDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerByFirstName(String firstName) {

        return customerMapper.customerToCustomerDTO(customerRepository.findByFirstName(firstName));
    }
}
