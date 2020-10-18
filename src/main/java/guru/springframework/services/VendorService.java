package guru.springframework.services;

import java.util.List;

import guru.springframework.api.v1.model.VendorDTO;

public interface VendorService {
	
	List<VendorDTO> getAllVendors();
	
	VendorDTO getVendorById(Long id);
	
	VendorDTO createNewVendor(VendorDTO vendorDTO);
	
	VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);
	
	VendorDTO patchVendorByDTO(Long id, VendorDTO vendorDTO);
	
	void deleteVendorById(Long id);
}
