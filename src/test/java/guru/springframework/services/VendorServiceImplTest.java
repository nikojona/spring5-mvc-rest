package guru.springframework.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@TestInstance(Lifecycle.PER_CLASS)
public class VendorServiceImplTest {

	public static final String NAME_1 = "Fruit Academy";
	public static final String NAME_2 = "Baby Fruit";
	
	public static final Long ID_1 = 1L;
	public static final Long ID_2 = 2L;
	
	@Mock
	VendorRepository vendorRepository;
	
	VendorService vendorService;
	
	@BeforeAll
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
	}
	
	@Test
	public void testGetVendorById() throws Exception {
		
		// given
		Vendor vendor = getVendor1();
		
		// mockito BDD Syntax
		given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
		
		// when
		VendorDTO vendorDTO = vendorService.getVendorById(1L);
		
		// then
		then(vendorRepository).should(times(1)).findById(anyLong());
		
		// JUnit assert that with matchers
		assertThat(vendorDTO.getName(), is(equalTo(NAME_1)));
	}
	
	
	@Test
	public void testGetVendorByIdNotFound() {
		
//		assertThrows(NullPointerException.class, () -> { // it will error
		assertThrows(ResourceNotFoundException.class, () -> {

			// given
			// mockito BDD syntax since mockito 1.10.0
			given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());
			
			// when
			VendorDTO vendorDTO = vendorService.getVendorById(1L);
			
			// then
			then(vendorRepository).should(times(1)).findById(anyLong());
			
		});
		
	}
	
	@Test
	public void testGetAllVendors() throws Exception {
		
		// given
		List<Vendor> vendors = Arrays.asList(getVendor1(), getVendor2());
		given(vendorRepository.findAll()).willReturn(vendors);
		
		// when
		VendorListDTO vendorListDto = vendorService.getAllVendors();
		
		// then
		then(vendorRepository).should(times(1)).findAll();
		assertThat(vendorListDto.getVendors().size(), is(equalTo(2)));
	}
	
	@Test
	public void testCreateNewVendor() throws Exception {
		
		// given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME_1);
		
		Vendor vendor = getVendor1();
		
		given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);
		
		// when
		VendorDTO savedDto = vendorService.createNewVendor(vendorDTO);
		
		// then
		// 'should' default to times = 1
//		then(vendorRepository).should().save(any(Vendor.class));
		then(vendorRepository).should(atLeastOnce()).save(any(Vendor.class));
		
		assertThat(savedDto.getVendorUrl(), containsString("1"));
	}
	
	@Test
	public void testSaveVendorByDTO() throws Exception {
		
		// given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME_1);
		
		Vendor vendor = getVendor1();
		
		given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);
		
		// when
		VendorDTO savedDto = vendorService.saveVendorByDTO(ID_1, vendorDTO);
		
		// then
		// 'should' defaults to time = 1
//		then(vendorRepository).should().save(any(Vendor.class));
		then(vendorRepository).should(atLeastOnce()).save(any(Vendor.class));
		
		assertThat(savedDto.getVendorUrl(), containsString("1"));
		
	}
	
	@Test
	public void testPatchVendor() throws Exception {
		
		// given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME_1);
		
		Vendor vendor = getVendor1();
		
		given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
		given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);
		
		// when
		VendorDTO savedDto = vendorService.patchVendor(ID_1, vendorDTO);
		
		// then
		then(vendorRepository).should().save(any(Vendor.class));
		
		// 'should' defaults to time = 1
//		then(vendorRepository).should(times(1)).findById(anyLong());
		then(vendorRepository).should(atLeast(1)).findById(anyLong());
		
		assertThat(savedDto.getVendorUrl(), containsString("1"));
		assertNotNull(vendorDTO);
		
	}
	
	@Test
	public void testDeleteVendorById() throws Exception {
		
		// when
		vendorRepository.deleteById(1L);
		
		// then
		then(vendorRepository).should().deleteById(anyLong());
		
	}
		
	private Vendor getVendor1() {
		
		Vendor vendor = new Vendor();
		vendor.setId(ID_1);
		vendor.setName(NAME_1);
		
		return vendor;
	}
	
	private Vendor getVendor2() {
		
		Vendor vendor = new Vendor();
		vendor.setId(ID_2);
		vendor.setName(NAME_2);
		
		return vendor;
	}
}
