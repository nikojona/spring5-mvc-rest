package guru.springframework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.controllers.v1.CustomerController;
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
                customerDto.setCustomerUrl(getCustomerUrl(customer.getId()));
                return customerDto;
            })
            .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {

        return customerRepository.findById(id)
            .map(customerMapper::customerToCustomerDTO)
            .map(customerDTO -> {
            	
            	// Set API URL
            	customerDTO.setCustomerUrl(getCustomerUrl(id));
            	
            	return customerDTO;
            })
            .orElseThrow(ResourceNotFoundException::new); 
    }
    
    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
    	
    	/**
    	 // you can do this way also 
	    	Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
	    	Customer savedCustomer = customerRepository.save(customer);
	    	CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
	    	returnDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
	    	return returnDTO;
    	 */
    	
    	return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));
    }
        
    private CustomerDTO saveAndReturnDTO(Customer customer) {
    	
    	Customer savedCustomer = customerRepository.save(customer);
    	
    	CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);
    	
    	returnDto.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
    	
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
			
			returnDTO.setCustomerUrl(getCustomerUrl(id));
			
			return returnDTO;
			
    	}).orElseThrow(ResourceNotFoundException::new); 
    }

	@Override
	public void deleteCustomerById(Long id) {
		
		customerRepository.deleteById(id);
	}
	
	private String getCustomerUrl(Long id) {
		
		return CustomerController.BASE_URL + "/" + id;
	}
    
}
