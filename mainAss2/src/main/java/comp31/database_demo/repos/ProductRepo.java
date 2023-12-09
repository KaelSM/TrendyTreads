package comp31.database_demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comp31.database_demo.model.Brand;
import comp31.database_demo.model.Product;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    
    /**
     * Retrieves a list of products by brand.
     *
     * @param brand the brand to filter the products by
     * @return a list of products matching the specified brand
     */
    List<Product> findByBrand(Brand brand);

    /**
     * Retrieves all products by brand.
     *
     * @param brand the brand to filter the products by
     * @return a list of all products matching the specified brand
     */
    List<Product> findAllByBrand(Brand brand);
}