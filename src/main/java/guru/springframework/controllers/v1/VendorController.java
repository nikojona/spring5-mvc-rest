package guru.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "This is my Vendor API")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
	
	public static final String BASE_URL = "/api/v1/vendors";
	
	private final VendorService vendorService;

	public VendorController(VendorService vendorService) {

		this.vendorService = vendorService;
	}
	
	@ApiOperation(value = "View List of Vendors", notes = "These are some API notes")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public VendorListDTO getVendorsList() {
		
		return vendorService.getAllVendors();
	}
	
	@ApiOperation(value = "Get Vendor by Id")
	@GetMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO getVendorById(@PathVariable Long id) {
		
		return vendorService.getVendorById(id);
	}
	
	@ApiOperation(value = "Create a new Vendor")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
		
		return vendorService.createNewVendor(vendorDTO);
	}
	
	@ApiOperation(value = "Update existing Vendor")
	@PutMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
		
		return vendorService.saveVendorByDTO(id, vendorDTO);
	}
	
	@ApiOperation(value = "Update a Vendor Property")
	@PatchMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
		
		return vendorService.patchVendor(id, vendorDTO);
	}
	
	@ApiOperation(value = "delete a Vendor")
	@DeleteMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public void deleteVendor(@PathVariable Long id) {
		
		vendorService.deleteVendorById(id);
	}
}
