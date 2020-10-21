package guru.springframework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService{
	
	private VendorMapper vendorMapper;
	private VendorRepository vendorRepository;

	public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
		
		this.vendorMapper = vendorMapper;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public VendorListDTO getAllVendors() {
		
		List<VendorDTO> vendorDTOs = vendorRepository
			.findAll()
			.stream()
			.map(vendor -> {
				VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
				vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
				return vendorDTO;
			})
			.collect(Collectors.toList());
		
		return new VendorListDTO(vendorDTOs);
	}

	@Override
	public VendorDTO getVendorById(Long id) {

		return vendorRepository.findById(id)
			.map(vendorMapper::vendorToVendorDto)
			.map(vendorDTO -> {
				
				// Set API Url
				vendorDTO.setVendorUrl(getVendorUrl(id));
				
				return vendorDTO;
			})
			.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public VendorDTO createNewVendor(VendorDTO vendorDTO) {
		
		return saveAndReturnDTO(vendorMapper.vendorDtoToVendor(vendorDTO));
	}

	@Override
	public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
		
		Vendor vendor =  vendorMapper.vendorDtoToVendor(vendorDTO);
		vendor.setId(id);
		
		return saveAndReturnDTO(vendor);
	}

	@Override
	public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
		
		return vendorRepository.findById(id)
			.map(vendor -> {
				
				// todo if more properties, add more if statements
//				if(vendorDTO.getName() != null) {
//					vendor.setName(vendorDTO.getName());
//				}
				
				vendor.setName(vendorDTO.getName());
					
				return saveAndReturnDTO(vendor);
			
		}).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteVendorById(Long id) {

		vendorRepository.deleteById(id);
	}
	
	private String getVendorUrl(Long id) {
		
		return VendorController.BASE_URL + "/" + id;
	}
	
	private VendorDTO saveAndReturnDTO(Vendor vendor) {
		
		Vendor savedVendor = vendorRepository.save(vendor);
		
		VendorDTO returnDto = vendorMapper.vendorToVendorDto(savedVendor);
		
		returnDto.setVendorUrl(getVendorUrl(savedVendor.getId()));
		
		return returnDto;
	}	
	
}
