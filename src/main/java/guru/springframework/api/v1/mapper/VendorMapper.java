package guru.springframework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;

@Mapper
public interface VendorMapper {
	
	VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);
	
	VendorDTO vendorToVendorDto(Vendor vendor);
	
	Vendor vendorDtoToVendor(VendorDTO vendorDTO);
}
