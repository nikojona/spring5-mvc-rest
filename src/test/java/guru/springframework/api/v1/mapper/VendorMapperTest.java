package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VendorMapperTest {

	public static final String NAME = "Fresh and Juicy";
	public static final Long ID = 1L;
	
	VendorMapper vendorMapper = VendorMapper.INSTANCE;
	
	@Test
	public void vendorToVendorDto() throws Exception {
		
		// given
		Vendor vendor = new Vendor();
		vendor.setId(ID);
		vendor.setName(NAME);
		
		// when
		VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
		
		// then
		assertEquals(Long.valueOf(ID), vendorDTO.getId());
		assertEquals(String.valueOf(NAME), vendorDTO.getName());
	}
	
	@Test
	public void vendorDtoToVendor() throws Exception {
		
		//given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setId(ID);
		vendorDTO.setName(NAME);
		
		// when
		Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
		
		// then
		assertEquals(Long.valueOf(ID), vendor.getId());
		assertEquals(String.valueOf(NAME), vendor.getName());
	}
}