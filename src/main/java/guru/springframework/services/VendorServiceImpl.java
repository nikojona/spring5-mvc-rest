package guru.springframework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
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
	
	@Autowired
	public void setVendorMapper(VendorMapper vendorMapper) {
		this.vendorMapper = vendorMapper;
	}

	@Autowired
	public void setVendorRepository(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}

	@Override
	public List<VendorDTO> getAllVendors() {
		
		return vendorRepository
			.findAll()
			.stream()
			.map(vendor -> {
				VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
				vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
				return vendorDTO;
			})
			.collect(Collectors.toList());
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
	public VendorDTO patchVendorByDTO(Long id, VendorDTO vendorDTO) {
		
		return vendorRepository.findById(id).map(vendor -> {
			
			if(vendorDTO.getName() != null) {
				vendor.setName(vendorDTO.getName());
			}
			
			VendorDTO returnDto = vendorMapper.vendorToVendorDto(vendorRepository.save(vendor));
			
			returnDto.setVendorUrl(getVendorUrl(id));
			
			return returnDto;
			
		}).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteVendorById(Long id) {

		vendorRepository.deleteById(id);
	}
	
	private VendorDTO saveAndReturnDTO(Vendor vendor) {
		
		Vendor savedVendor = vendorRepository.save(vendor);
		
		VendorDTO returnDto = vendorMapper.vendorToVendorDto(savedVendor);
		
		returnDto.setVendorUrl(getVendorUrl(savedVendor.getId()));
		
		return returnDto;
	}
	
	private String getVendorUrl(Long id) {
		
		return VendorController.BASE_URL + "/" + id;
	}
	
}
