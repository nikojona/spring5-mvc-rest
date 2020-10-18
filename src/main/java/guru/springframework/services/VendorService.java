package guru.springframework.services;

import java.util.List;

import guru.springframework.api.v1.model.VendorDTO;

public interface VendorService {
	
	List<VendorDTO> getAllVendors();
	
	VendorDTO getVendorById(Long id);
	
	VendorDTO createNewVendor(VendorDTO vendorDTO);
	
	VendorDTO saveVendorByDTO(VendorDTO vendorDTO, Long id);
	
	VendorDTO patchVendorByDTO(VendorDTO vendorDTO, Long id);
	
	void deleteVendorById(Long id);
}
