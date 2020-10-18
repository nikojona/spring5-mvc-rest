package guru.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.services.VendorService;


@Controller
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
	
	public static final String BASE_URL = "/api/v1/vendors";
	
	private final VendorService vendorService;

	public VendorController(VendorService vendorService) {

		this.vendorService = vendorService;
	}

	@GetMapping
	public ResponseEntity<VendorListDTO> getAllVendors() {
		
		return new ResponseEntity<VendorListDTO>(
			new VendorListDTO(vendorService.getAllVendors()), HttpStatus.OK);
	}
	
	@GetMapping({"/{id}"})
	public ResponseEntity<VendorDTO> getVendorById(Long id) {
		
		return new ResponseEntity<VendorDTO>(vendorService.getVendorById(id), HttpStatus.OK);
	}
	
}
