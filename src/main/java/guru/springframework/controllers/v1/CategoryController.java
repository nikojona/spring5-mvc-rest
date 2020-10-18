package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.services.CategoryService;
import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity; // using @Contoller annotation
//import org.springframework.stereotype.Controller; // using @Contoller annotation
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//@Controller // using @Contoller annotation
@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
	
	public static final String BASE_URL = "/api/v1/categories/"; 
	
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {

		this.categoryService = categoryService;
	}
	
//	// Using @Controller annotation
//	@GetMapping
//	public ResponseEntity<CategoryListDTO> getAllCategories() {
//		
//		return new ResponseEntity<CategoryListDTO> (
//				new CategoryListDTO(categoryService.getAllCategories()), HttpStatus.OK);
//	}
//	
//	@GetMapping("{name}")
//	public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
//		
//		return new ResponseEntity<CategoryDTO> (
//				categoryService.getCategoryByName(name), HttpStatus.OK);
//	}
	
	// Using @RestController annotation to replace @Controller annotation
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CategoryListDTO getAllCategories() {
		
		return new CategoryListDTO(categoryService.getAllCategories());
	}
	
	@GetMapping("{name}")
	@ResponseStatus(HttpStatus.OK)
	public CategoryDTO getCategoryByName(@PathVariable String name) {
		
		return categoryService.getCategoryByName(name);
	}
}
