package guru.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.api.v1.mapper.CategoryMapper;
import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;

import static org.mockito.ArgumentMatchers.anyString;

public class CategoryServiceTest {
	
	public static final Long ID = 2L;
	public static final String NAME = "Jimmy";
	CategoryService categoryService;
	
	@Mock
	CategoryRepository categoryRepository;
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
	}
	
	@Test
	public void getAllCategories() {
		
		// given
		List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
		
		when(categoryRepository.findAll()).thenReturn(categories);
		
		// when
		List<CategoryDTO> categoryDTO = categoryService.getAllCategories();
		
		// then
		assertEquals(3, categoryDTO.size());
	}

	@Test
	public void getCategoryByName() {
		
		//given
		Category category = new Category();
		category.setId(ID);
		category.setName(NAME);
		
		when(categoryRepository.findByName(anyString())).thenReturn(category);
		
		// when
		CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);
		
		// then
		assertEquals(ID, categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
				
	}

}
