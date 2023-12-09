package comp31.database_demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import comp31.database_demo.model.Brand;
import comp31.database_demo.model.Product;
import comp31.database_demo.repos.BrandRepo;
import comp31.database_demo.repos.ProductRepo;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private BrandRepo brandRepository;

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product
     * @return the product with the specified ID
     * @throws RuntimeException if the product is not found
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    /**
     * Saves a product with the specified brand ID.
     *
     * @param product  the product to be saved
     * @param brandId  the ID of the brand associated with the product
     * @return the saved product
     * @throws RuntimeException if the brand is not found
     */
    public Product saveProduct(Product product, Long brandId) {
        Optional<Brand> brandOptional = brandRepository.findById(brandId);
        if (brandOptional.isPresent()) {
            product.setBrand(brandOptional.get());
        } else {
            throw new RuntimeException("Brand with id " + brandId + " not found");
        }
        return productRepository.save(product);
    }

    /**
     * Updates a product with the specified ID and brand ID.
     *
     * @param id             the ID of the product to be updated
     * @param productDetails the updated product details
     * @param brandId        the ID of the brand associated with the product
     * @return the updated product
     * @throws RuntimeException if the product or brand is not found
     */
    public Product updateProduct(Long id, Product productDetails, Long brandId) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    
        product.setName(productDetails.getName());
        product.setStock(productDetails.getStock());
        product.setPrice(productDetails.getPrice());
        if (brandId != null) {
            Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + brandId));
            product.setBrand(brand);
        }
    
        return productRepository.save(product);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to be deleted
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
