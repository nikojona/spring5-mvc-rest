package guru.springframework.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;

@Component
public class Bootstrap implements CommandLineRunner {

	private CategoryRepository categoryRepository;
	
	public Bootstrap(CategoryRepository categoryRepository) {
		
		this.categoryRepository = categoryRepository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		Category dried = new Category();
		dried.setName("Dried");
		
		Category fruits = new Category();
		fruits.setName("Fruits");
		
		Category exotic = new Category();
		exotic.setName("Exotic");
		
		Category nuts = new Category();
		nuts.setName("Nuts");
				
		categoryRepository.save(dried); 
		categoryRepository.save(fruits);
		categoryRepository.save(exotic);
		categoryRepository.save(nuts);
		
		System.out.println("Data loaded count = " + categoryRepository.count());
	}
	
	
}
