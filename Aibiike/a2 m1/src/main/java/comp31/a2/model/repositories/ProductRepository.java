package comp31.a2.model.repositories;

import java.util.List;
import java.util.Optional;

import comp31.a2.model.entities.Product;


public class ProductRepository extends JpaRepository<Product, Long> {

    public void deleteById(Long id) {
    }

    public List<Product> findAll() {
        return null;
    }

    public Optional<Product> findById(Long id) {
        return null;
    }

    public Product save(Product product) {
        return null;
    }
}
