package comp31.milestone1ass2.model.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import comp31.milestone1ass2.model.entities.Product;

public interface ProductRepository extends ListCrudRepository<Product, Long>{
    // Custom query method to find products by name
    List<Product> findByName(String name);
    // Save method to persist a product
    Product save(Product product);

    // Delete method to remove a product by ID
    void deleteById(Long id);
    // Custom query method to find products by price between two values
    List<Product> findByPriceBetween(Integer price1, Integer price2);
}
