package guru.springframework.controllers.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.services.VendorService;

@TestInstance(Lifecycle.PER_CLASS)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerTest extends AbstractRestControllerTest {
	
	@MockBean //provided by Spring Context
	VendorService vendorService;
	
	@Autowired
	MockMvc mockMvc; //provided by Spring Context
	
	VendorDTO vendorDTO_1;
	VendorDTO vendorDTO_2;
	
	@BeforeAll
	public void setUp() throws Exception {
		
		vendorDTO_1 = new VendorDTO("Vendor 1", VendorController.BASE_URL + "/1");
		vendorDTO_2 = new VendorDTO("Vendor 2", VendorController.BASE_URL + "/2");
	}
	
	@Test
	public void testGetVendorList() throws Exception {
		
		// given
		VendorListDTO vendorListDTO = new VendorListDTO(Arrays.asList(vendorDTO_1, vendorDTO_2));
		
		given(vendorService.getAllVendors()).willReturn(vendorListDTO);
		
		// then
		mockMvc.perform(get(VendorController.BASE_URL)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.vendors", hasSize(2)));
	}
	
	@Test
	public void testGetVendorsById() throws Exception {
		
		// given		
		given(vendorService.getVendorById(anyLong())).willReturn(vendorDTO_1);
		
		// then
		mockMvc.perform(get(VendorController.BASE_URL + "/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
	}
	
	@Test
	public void testCreateNewVendor() throws Exception {
		
		// givem
		given(vendorService.createNewVendor(vendorDTO_1)).willReturn(vendorDTO_1);
		
		// when
		mockMvc.perform(post(VendorController.BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendorDTO_1)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
			
	}
	
	@Test
	public void testUpdateVendor() throws Exception {
		
		// given
		given(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO_1);
		
		// then
		mockMvc.perform(put(VendorController.BASE_URL + "/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(vendorDTO_1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
	}
	
	@Test
	public void testPatchVendor() throws Exception {
		
		// when
		given(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO_1);
		
		// then
		mockMvc.perform(patch(VendorController.BASE_URL + "/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(vendorDTO_1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
		
	}
	
	@Test
	public void testDeleteVendor() throws Exception {
		
		// when
		mockMvc.perform(delete(VendorController.BASE_URL + "/1"))
			.andExpect(status().isOk());
		
		// then
		then(vendorService).should().deleteVendorById(anyLong());
	}
}
