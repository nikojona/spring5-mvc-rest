package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class VendorMapperTest {

	public static final String NAME = "Fresh and Juicy";
	public static final Long ID = 1L;
	
	VendorMapper vendorMapper = VendorMapper.INSTANCE;
	
	@Test
    public void testVendorToVendorDtoIfNull() throws Exception {
    	// given
		Vendor vendor = null;
    	
    	// when
		VendorDTO vendorDto = vendorMapper.vendorToVendorDto(vendor);
        assertNull(vendorDto);
    }
    
    @Test
    public void testVendorDtoToVendorIfNull() throws Exception {
    	// given
    	VendorDTO vendorDto = null;
    	
    	// when
    	Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDto);
        assertNull(vendor);
    }
	
	@Test
	public void vendorToVendorDto() throws Exception {
		
		// given
		Vendor vendor = new Vendor();
		vendor.setId(ID);
		vendor.setName(NAME);
		
		// when
		VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
		
		// then
		assertEquals(vendor.getName(), vendorDTO.getName());
	}
	
	@Test
	public void vendorDtoToVendor() throws Exception {
		
		//given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		
		// when
		Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
		
		// then
		assertEquals(vendorDTO.getName(), vendor.getName());
	}
}
