package guru.springframework.controllers.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
// import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetCustomersList() throws Exception {

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname(FIRST_NAME);
        customerDTO1.setLastname("Ken");
        customerDTO1.setCustomerUrl("/api/v1/customers/1");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstname("Celestyn");
        customerDTO2.setLastname("Key");
        customerDTO2.setCustomerUrl("/api/v1/customers/2");

        List<CustomerDTO> customers = Arrays.asList(customerDTO1, customerDTO2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers/")
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
        customerDTO1.setCustomerUrl("/api/v1/customers/1");

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO1);

        // then
        mockMvc.perform(get("/api/v1/customers/1")
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
    	returnDTO.setCustomerUrl("/api/v1/customers/1");
    	
    	when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);
    	
    	// when/then
    	mockMvc.perform(post("/api/v1/customers/")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(asJsonString(customerDTO)))
    		.andExpect(status().isCreated())
    		.andExpect(jsonPath("$.firstname", equalTo("Tom")))
			.andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    	
//    	// for debug purpose
//    	String response = mockMvc.perform(post("/api/v1/customers/")
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
    	returnDTO.setCustomerUrl("/api/v1/customers/1");
    	
    	when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);
    	
    	// when/then
    	mockMvc.perform(put("/api/v1/customers/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(customerDTO)))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.firstname", equalTo("Fred")))
    		.andExpect(jsonPath("$.lastname", equalTo("Flinstone")))
    		.andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    		
    }
}
