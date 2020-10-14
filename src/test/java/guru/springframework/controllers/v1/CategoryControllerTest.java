package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.services.CategoryService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(Lifecycle.PER_CLASS)
public class CategoryControllerTest {
	
	public static final String NAME = "Jim";
	
	@Mock
	CategoryService categoryService;
	
	@InjectMocks
	CategoryController categoryController;
	
	MockMvc mockMvc;
	
	@BeforeAll
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}
	
	@Test
	public void testListCategories() throws Exception {
		
		CategoryDTO categoryDTO1 = new CategoryDTO();
		categoryDTO1.setId(1L);
		categoryDTO1.setName(NAME);
		
		CategoryDTO categoryDTO2 = new CategoryDTO();
		categoryDTO2.setId(2L);	
		categoryDTO2.setName("Bob");
		
		List<CategoryDTO> categories = Arrays.asList(categoryDTO1, categoryDTO2);
		
		when(categoryService.getAllCategories()).thenReturn(categories);
		
		mockMvc.perform(get("/api/v1/categories/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.categories", hasSize(2)));
		
	}
	
	@Test
	public void testGetByNameCategories() throws Exception {
		
		CategoryDTO categoryDTO1 = new CategoryDTO();
		categoryDTO1.setId(1L);
		categoryDTO1.setName(NAME);
		
		when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO1);
		
		mockMvc.perform(get("/api/v1/categories/Jim")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(NAME)));
	}

}
