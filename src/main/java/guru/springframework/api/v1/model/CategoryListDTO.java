package guru.springframework.api.v1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryListDTO {
	
	List<CategoryDTO> categories;

	public CategoryListDTO(List<CategoryDTO> categories) {
		this.categories = categories;
	}
	
	
}
