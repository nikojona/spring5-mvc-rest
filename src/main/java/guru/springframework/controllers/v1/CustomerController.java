package guru.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.services.CustomerService;

@Controller
@RequestMapping("/api/v1/customers/")
public class CustomerController {
    
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public ResponseEntity<CustomerListDTO> getAllCustomers() throws Exception {

        return new ResponseEntity<CustomerListDTO>(
            new CustomerListDTO(customerService.getAllCustomers()), HttpStatus.OK);
    }

    public ResponseEntity<CustomerDTO> getCustomerByFirstName(@PathVariable String firstName) throws Exception {
        
        return new ResponseEntity<CustomerDTO> (
            customerService.getCustomerByFirstName(firstName), HttpStatus.OK);
    }

}
