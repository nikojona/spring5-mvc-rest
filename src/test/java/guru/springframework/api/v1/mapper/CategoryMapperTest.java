package guru.springframework.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;

public class CategoryMapperTest {
	
	public static final String NAME = "Joe";
	public static final Long ID = 1L;
	
	CategoryMapper categoryMapper = CategoryMapper.INSTANCE; 
	
	@Test
	public void categoryToCategoryDTO() throws Exception {
		
		// given
		Category category = new Category();
		category.setName(NAME);
		category.setId(ID);
		
		// when
		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
		
		// then
		assertEquals(Long.valueOf(ID), categoryDTO.getId());
		assertEquals(String.valueOf(NAME), categoryDTO.getName());
	}	

}