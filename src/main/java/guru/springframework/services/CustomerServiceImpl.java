package guru.springframework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
    
    private CustomerMapper customerMapper;
    private CustomerRepository customerRepository;
    
    @Autowired
	public void setCustomerMapper(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}


    @Autowired
	public void setCustomerRepository(CustomerRepository customerRepository) {
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
    public CustomerDTO getCustomerById(Long id) {

        return customerRepository.findById(id)
            .map(customerMapper::customerToCustomerDTO)
            .orElseThrow(RuntimeException::new); // todo implement better exception handling
    }
    
    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
    	
    	Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
    	
    	Customer savedCustomer = customerRepository.save(customer);
    	
    	CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
    	
    	returnDTO.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());
    	
    	return returnDTO;
    }
        
    private CustomerDTO saveAndReturnDTO(Customer customer) {
    	
    	Customer savedCustomer = customerRepository.save(customer);
    	
    	CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);
    	
    	returnDto.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());
    	
    	return returnDto;
    }
    
    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
    	
    	Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
    	customer.setId(id);
    	
    	return saveAndReturnDTO(customer);
    }
    
}
