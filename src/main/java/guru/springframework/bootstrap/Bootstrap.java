package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
	
	private final CategoryRepository categoryRepository;
	private final CustomerRepository customerRepository;
	private final VendorRepository vendorRepository;
	
	
	public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository,
			VendorRepository vendorRepository) {

		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		loadCustomers();

		loadCategories();
		
		loadVendors();

	}
	
	private void loadVendors() {
		Vendor ranchMarket = new Vendor();
		ranchMarket.setName("Ranch Market");

		Vendor happyJuice = new Vendor();
		happyJuice.setName("Happy Juice Market");
		
		Vendor fruitAnatomy = new Vendor();
		fruitAnatomy.setName("Fruit Anatomy");
		
		Vendor exoticFruits = new Vendor();
		exoticFruits.setName("Exotic Fruits");
		
		Vendor crazyAndNuts = new Vendor();
		crazyAndNuts.setName("Crazy and Nuts");
				
		vendorRepository.save(ranchMarket);
		vendorRepository.save(happyJuice);
		vendorRepository.save(fruitAnatomy);
		vendorRepository.save(exoticFruits);
		vendorRepository.save(crazyAndNuts);
		
		System.out.println("Data Vendor loaded count = " + vendorRepository.count());
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
