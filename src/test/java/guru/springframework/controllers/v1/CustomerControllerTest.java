package guru.springframework.controllers.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.controllers.RestResponseEntityExceptionHandler;
import guru.springframework.services.CustomerService;

@TestInstance(Lifecycle.PER_CLASS)
public class CustomerControllerTest extends AbstractRestControllerTest {

    public static final String FIRST_NAME = "Daniel";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;
    
    @BeforeAll
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
        		.setControllerAdvice(new RestResponseEntityExceptionHandler())
        		.build();
    }

    @Test
    public void testGetCustomersList() throws Exception {

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname(FIRST_NAME);
        customerDTO1.setLastname("Ken");
        customerDTO1.setCustomerUrl(CustomerController.BASE_URL + "/1");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstname("Celestyn");
        customerDTO2.setLastname("Key");
        customerDTO2.setCustomerUrl(CustomerController.BASE_URL + "/2");

        List<CustomerDTO> customers = Arrays.asList(customerDTO1, customerDTO2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get(CustomerController.BASE_URL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomersById() throws Exception {

        // given
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("Luke");
        customerDTO1.setLastname("Skywalker");
        customerDTO1.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO1);

        // then
        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstname", equalTo("Luke")));

    }
    
    @Test
    public void testCreateNewCustomer() throws Exception {
    	
    	// given
    	CustomerDTO customerDTO = new CustomerDTO();
    	customerDTO.setFirstname("Tom");
    	customerDTO.setLastname("Cruise");
    	
    	CustomerDTO returnDTO = new CustomerDTO();
    	returnDTO.setFirstname(customerDTO.getFirstname());
    	returnDTO.setLastname(customerDTO.getLastname());
    	returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");
    	
    	when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);
    	
    	// when/then
    	mockMvc.perform(post(CustomerController.BASE_URL)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(asJsonString(customerDTO)))
    		.andExpect(status().isCreated())
    		.andExpect(jsonPath("$.firstname", equalTo("Tom")))
			.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    	
//    	// for debug purpose
//    	String response = mockMvc.perform(post(CustomerController.BASE_URL)
//    			.contentType(MediaType.APPLICATION_JSON)
//    			.content(asJsonString(customerDTO)))
//    			.andReturn().getResponse().getContentAsString();
//    	
//    	System.out.println(response);
    }
    
    @Test
    public void testUpdateCustomer() throws Exception {
    	
    	// given
    	CustomerDTO customerDTO = new CustomerDTO();
    	customerDTO.setFirstname("Fred");
    	customerDTO.setLastname("Flinstone");
    	
    	CustomerDTO returnDTO = new CustomerDTO();
    	returnDTO.setFirstname(customerDTO.getFirstname());
    	returnDTO.setLastname(customerDTO.getLastname());
    	returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");
    	
    	when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);
    	
    	// when/then
    	mockMvc.perform(put(CustomerController.BASE_URL + "/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(customerDTO)))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.firstname", equalTo("Fred")))
    		.andExpect(jsonPath("$.lastname", equalTo("Flinstone")))
    		.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    		
    }
    
    @Test
    public void testPatchCustomer() throws Exception {
    	
    	// given
    	CustomerDTO customerDTO = new CustomerDTO();
    	customerDTO.setFirstname("Alan");
    	
    	CustomerDTO returnDTO = new CustomerDTO();
    	returnDTO.setFirstname(customerDTO.getFirstname());
    	returnDTO.setLastname("Budiansyah");
    	returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");
    	
    	when(customerService.patchCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);
    	
    	// when
    	mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
    		.contentType(MediaType.APPLICATION_JSON)
    		.content(asJsonString(customerDTO)))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.firstname", equalTo("Alan")))
    		.andExpect(jsonPath("$.lastname", equalTo("Budiansyah")))
    		.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    		
    }
    
    @Test
    public void testDeleteCustomer() throws Exception {
    	
    	// given
    	mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk());
    	
    	// when
    	verify(customerService).deleteCustomerById(anyLong());
    }
}
