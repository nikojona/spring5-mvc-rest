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
    
    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
		this.customerMapper = customerMapper;
		this.customerRepository = customerRepository;
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

        CustomerDTO returnDTO = customerRepository.findById(id)
            .map(customerMapper::customerToCustomerDTO)
            .orElseThrow(RuntimeException::new); // todo implement better exception handling
        
		returnDTO.setCustomerUrl("/api/v1/customers/" + id);
		
		return returnDTO;
    }
    
    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
    	
    	/**
    	 // you can do this way also 
	    	Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
	    	Customer savedCustomer = customerRepository.save(customer);
	    	CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
	    	returnDTO.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());
	    	return returnDTO;
    	 */
    	
    	return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));
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
    
    @Override
    public CustomerDTO patchCustomerByDTO(Long id, CustomerDTO customerDTO) {
    	
    	return customerRepository.findById(id).map(customer -> {
    		
			if(customerDTO.getFirstname() != null) {
				customer.setFirstname(customerDTO.getFirstname());
			}
			
			if(customerDTO.getLastname() != null) {
				customer.setLastname(customerDTO.getLastname());
			}
			
			CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
			
			returnDTO.setCustomerUrl("/api/v1/customers/" + id);
			
			return returnDTO;
			
    	}).orElseThrow(RuntimeException::new); // todo implement better exception handling
    }
    
}
