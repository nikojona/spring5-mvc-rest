package guru.springframework.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@TestInstance(Lifecycle.PER_CLASS)
public class VendorServiceImplTest {

	@Mock
	VendorRepository vendorRepository;
	
	VendorMapper vendorMapper = VendorMapper.INSTANCE;
	
	VendorServiceImpl vendorService;
	
	@BeforeAll
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		vendorService = new VendorServiceImpl(vendorMapper, vendorRepository);
	}
	
	@Test
	public void testGetAllVendors() throws Exception {
		
		// given
		Vendor vendor1 = new Vendor();
		vendor1.setId(1L);
		vendor1.setName("Fruit Anatomy");
		
		Vendor vendor2 = new Vendor();
		vendor2.setId(2L);
		vendor2.setName("Heroic Fruits");
		
		when(vendorRepository.findAll()).thenReturn(Arrays.asList(vendor1, vendor2));
		
		// when
		List<VendorDTO> vendorsDTO = vendorService.getAllVendors();
		
		// then
		assertEquals(2, vendorsDTO.size());
	}
	
	@Test
	public void testGetVendorById() throws Exception {
		
		// given
		Vendor vendor = new Vendor();
		vendor.setId(1L);
		vendor.setName("Fruits Assasins");
		
		when(vendorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(vendor));
		
		// when
		VendorDTO vendorDTO = vendorService.getVendorById(1L);
		
		assertEquals("Fruits Assasins", vendorDTO.getName());
	}
	
	@Test
	public void testCreateNewVendor() throws Exception {
		
		// given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setId(1L);
		vendorDTO.setName("Fruitymaniacs");
		
		Vendor savedVendor = new Vendor();
		savedVendor.setId(vendorDTO.getId());
		savedVendor.setName(vendorDTO.getName());
		
		when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
		
		// when
		VendorDTO savedDto = vendorService.saveVendorByDTO(1L, vendorDTO);
		
		// then
		assertEquals(vendorDTO.getName(), savedDto.getName());
		assertEquals(VendorController.BASE_URL + "/1", savedDto.getVendorUrl());
	}
	
	@Test
	public void testSaveVendorByDTO() throws Exception {
		
		// given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName("Baby Fruit");
		
		Vendor savedVendor = new Vendor();
		savedVendor.setId(1L);
		savedVendor.setName(vendorDTO.getName());
		
		when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
		
		// when
		VendorDTO savedDto = vendorService.saveVendorByDTO(1L, vendorDTO);
		
		// then
		assertEquals(vendorDTO.getName(), savedDto.getName());
		assertEquals(VendorController.BASE_URL + "/1", savedDto.getVendorUrl());
		
	}
	
	@Test
	public void testDeleteVendorByDTO() throws Exception {
		
		Long id = 1L;
		vendorRepository.deleteById(id);
		
		verify(vendorRepository, times(1)).deleteById(anyLong());
	}
}
