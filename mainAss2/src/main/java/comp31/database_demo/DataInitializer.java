package comp31.database_demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import comp31.database_demo.model.Brand;
import comp31.database_demo.model.Product;
import comp31.database_demo.model.User;
import comp31.database_demo.services.BrandService;
import comp31.database_demo.services.ProductService;
import comp31.database_demo.services.UserService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setUsername("admin");
        admin.setName("Administrator");
        admin.setPassword("adminpass"); // You should encrypt this
        admin.setRole("ADMIN");
        userService.saveUser(admin);

        User user = new User();
        user.setUsername("user");
        user.setName("User");
        user.setPassword("userpass"); // And this too
        user.setRole("USER");
        userService.saveUser(user);

        Brand brand1 = new Brand();
        brand1.setName("DC");
        brandService.saveBrand(brand1);

        Brand brand2 = new Brand();
        brand2.setName("Timberland");
        brandService.saveBrand(brand2);

        Brand brand3 = new Brand();
        brand3.setName("Dr. Martens");
        brandService.saveBrand(brand3);

        Brand brand4 = new Brand();
        brand4.setName("Crocs");
        brandService.saveBrand(brand4);

        Product product1 = new Product();
        product1.setName("COURT GRAFFIK");
        product1.setStock(100);
        product1.setPrice(90.00);
        product1.setBrand(brand1);
        productService.saveProduct(product1, brand1.getId());

        Product product2 = new Product();
        product2.setName("6-INCH PREMIUM WATERPROOF BOOTS");
        product2.setStock(30);
        product2.setPrice(198.00);
        product2.setBrand(brand2);
        productService.saveProduct(product2, brand2.getId());

        Product product3 = new Product();
        product3.setName("1460 SMOOTH LEATHER LACE UP BOOTS");
        product3.setStock(50);
        product3.setPrice(350.00);
        product3.setBrand(brand3);
        productService.saveProduct(product3, brand3.getId());

        Product product4 = new Product();
        product4.setName("KIDS' CLASSIC POKEMON CLOG");
        product4.setStock(20);
        product4.setPrice(45.00);
        product4.setBrand(brand4);
        productService.saveProduct(product4, brand4.getId());
    }
}