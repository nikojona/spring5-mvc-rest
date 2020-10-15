package guru.springframework.controllers.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
// import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class CustomerControllerTest {

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
        customerDTO1.setFirstName(FIRST_NAME);
        customerDTO1.setLastName("Ken");
        customerDTO1.setCustomerUrl("/api/v1/customers/1");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstName("Celestyn");
        customerDTO2.setLastName("Key");
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
        customerDTO1.setFirstName("Luke");
        customerDTO1.setLastName("Skywalker");
        customerDTO1.setCustomerUrl("/api/v1/customers/1");

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO1);

        // then
        mockMvc.perform(get("/api/v1/customers/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", equalTo("Luke")));

    }

    // @Test
    // public void testGetCustomersByFirstName() throws Exception {

    //     CustomerDTO customerDTO1 = new CustomerDTO();
    //     customerDTO1.setFirstName(FIRST_NAME);
    //     customerDTO1.setLastName("Ken");
    //     customerDTO1.setId(1L);

    //     when(customerService.getCustomerByFirstName(anyString())).thenReturn(customerDTO1);

    //     mockMvc.perform(get("/api/v1/customers/Daniel")
    //         .contentType(MediaType.APPLICATION_JSON))
    //         .andExpect(status().isOk())
    //         .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)));
    
    // }
}
