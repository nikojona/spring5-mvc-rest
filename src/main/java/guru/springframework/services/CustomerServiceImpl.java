package guru.springframework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
    
    public final CustomerMapper customerMapper;
    public final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }
    
    @Override
    public List<CustomerDTO> getAllCustomers() {

        return customerRepository
            .findAll()
            .stream()
            .map(customer -> {
                CustomerDTO customerDto = customerMapper.customerToCustomerDTO(customer);
                customerDto.setCustomerUrl("/api/v1/customers/" + customer.getId());
                return customerDto;
            })
            .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerByFirstName(String firstName) {

        return customerMapper.customerToCustomerDTO(customerRepository.findByFirstName(firstName));
    }
}
