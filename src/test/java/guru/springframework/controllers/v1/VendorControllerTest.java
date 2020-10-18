package guru.springframework.controllers.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.RestResponseEntityExceptionHandler;
import guru.springframework.services.VendorService;


@TestInstance(Lifecycle.PER_CLASS)
public class VendorControllerTest extends AbstractRestControllerTest {
	
	public static String NAME = "Crazy and Nuts";
	
	@Mock
	VendorService vendorService;
	
	@InjectMocks
	VendorController vendorController;
	
	MockMvc mockMvc;
	
	@BeforeAll
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}
	
	@Test
	public void testGetVendorsList() throws Exception {
		
		// given
		VendorDTO vendorDTO1 =  new VendorDTO();
		vendorDTO1.setName("Honolulu Fruits");
		vendorDTO1.setVendorUrl(VendorController.BASE_URL + "/1");
		
		VendorDTO vendorDTO2 = new VendorDTO();
		vendorDTO2.setName("Ranch Market");
		vendorDTO2.setVendorUrl(VendorController.BASE_URL + "/2");
		
		List<VendorDTO> vendors = Arrays.asList(vendorDTO1, vendorDTO2);
		
		when(vendorService.getAllVendors()).thenReturn(vendors);
		
		// then
		mockMvc.perform(get(VendorController.BASE_URL)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.vendors", hasSize(2)));
	}
	
	@Test
	public void testGetVendorsById() throws Exception {
		
		// given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		vendorDTO.setVendorUrl(VendorController.BASE_URL + "/1");
		
		when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);
		
		// then
		mockMvc.perform(get(VendorController.BASE_URL + "/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(NAME)));
	}
	
	@Test
	public void testCreateNewVendor() throws Exception {
		
		// givem
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		
		VendorDTO returnDTO = new VendorDTO();
		returnDTO.setName(vendorDTO.getName());
		returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");
		
		when(vendorService.createNewVendor(vendorDTO)).thenReturn(returnDTO);
		
		// when
		mockMvc.perform(post(VendorController.BASE_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(vendorDTO)))
			.andExpect(jsonPath("$.name", equalTo(NAME)))
			.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
			
	}
	
	@Test
	public void testUpdateVendor() throws Exception {
		
		// given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		
		VendorDTO returnDTO = new VendorDTO();
		returnDTO.setName(vendorDTO.getName());
		returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");
		
		when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);
		
		// then
		mockMvc.perform(put(VendorController.BASE_URL + "/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(vendorDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(NAME)))
			.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
			
	}
	
	@Test
	public void testPatchVendor() throws Exception {
		
		// when
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		
		VendorDTO returnDTO = new VendorDTO();
		returnDTO.setName(vendorDTO.getName());
		returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");
		
		when(vendorService.patchVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);
		
		// then
		mockMvc.perform(patch(VendorController.BASE_URL + "/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(vendorDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(NAME)))
			.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
		
	}
	
	@Test
	public void testDeleteVendor() throws Exception {
		
		// when
		mockMvc.perform(delete(VendorController.BASE_URL + "/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		// when
		verify(vendorService).deleteVendorById(anyLong());
	}
}
