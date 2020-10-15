package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
	
	private final CategoryRepository categoryRepository;
	private final CustomerRepository customerRepository;
	
	public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		
		loadCustomers();

		loadCategories();


	}


	private void loadCategories() {
		Category fruits = new Category();
		fruits.setName("Fruits");

		Category dried = new Category();
		dried.setName("Dried");
		
		Category fresh = new Category();
		fresh.setName("Fresh");
		
		Category exotic = new Category();
		exotic.setName("Exotic");
		
		Category nuts = new Category();
		nuts.setName("Nuts");
				
		categoryRepository.save(fruits);
		categoryRepository.save(dried);
		categoryRepository.save(fresh);
		categoryRepository.save(exotic);
		categoryRepository.save(nuts);
		
		System.out.println("Data Category loaded count = " + categoryRepository.count());
	}

	private void loadCustomers() {
		Customer david = new Customer();
		david.setFirstname("David");
		david.setLastname("Wine");

		Customer anne = new Customer();
		anne.setFirstname("Anne");
		anne.setLastname("Hine");

		Customer alice = new Customer();
		alice.setFirstname("Alice");
		alice.setLastname("Eastman");

		Customer zsolt = new Customer();
		zsolt.setFirstname("Zsolt");
		zsolt.setLastname("Torok");
		
		Customer sam = new Customer();
		sam.setFirstname("Sam");
		sam.setLastname("Axe");

		customerRepository.save(david);
		customerRepository.save(anne);
		customerRepository.save(alice);
		customerRepository.save(zsolt);
		customerRepository.save(sam);

		System.out.println("Data Customer loaded count = " + customerRepository.count());
	}
	
}
