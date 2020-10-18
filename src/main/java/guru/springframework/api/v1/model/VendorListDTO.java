package guru.springframework.api.v1.model;

import java.util.List;

import guru.springframework.domain.Vendor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorListDTO {
	
	List<VendorDTO> vendors;
}