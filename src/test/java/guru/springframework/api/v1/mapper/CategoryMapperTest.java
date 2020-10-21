package guru.springframework.api.v1.mapper;

import org.junit.jupiter.api.Test;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CategoryMapperTest {
	
	public static final String NAME = "Joe";
	public static final Long ID = 1L;
	
	CategoryMapper categoryMapper = CategoryMapper.INSTANCE; 
	
	@Test
    public void testCategoryToCategoryDtoIfNull() throws Exception {
    	// given
		Category category = null;
    	
    	// when
		CategoryDTO categoryDto = categoryMapper.categoryToCategoryDTO(category);
        assertNull(categoryDto);
    }
	
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
