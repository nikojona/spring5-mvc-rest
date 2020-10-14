package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

	private CategoryRepository categoryRepository;
	private CustomerRepository customerRepository;

	public Bootstrap(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public Bootstrap(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
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

		Customer david = new Customer();
		david.setFirstName("David");
		david.setLastName("Wine");

		Customer anne = new Customer();
		anne.setFirstName("Anne");
		anne.setLastName("Hine");

		Customer alice = new Customer();
		alice.setFirstName("Alice");
		alice.setLastName("Eastman");

		Customer zsolt = new Customer();
		zsolt.setFirstName("Zsolt");
		zsolt.setLastName("Torok");
		
		Customer sam = new Customer();
		sam.setFirstName("Sam");
		sam.setLastName("Axe");

		customerRepository.save(david);
		customerRepository.save(anne);
		customerRepository.save(alice);
		customerRepository.save(zsolt);
		customerRepository.save(sam);

		System.out.println("Data Customer loaded count = " + customerRepository.count());

	}
	
	
}
