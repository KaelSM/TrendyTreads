package comp31.database_demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comp31.database_demo.model.CartItem;
import comp31.database_demo.model.Feedback;
import comp31.database_demo.model.Product;
import comp31.database_demo.repos.CartItemRepo;
import comp31.database_demo.repos.ProductRepo;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * ProductService is a service class that handles the business logic for the Product model
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private CartItemRepo cartItemRepository;

    /**
     * Saves a new product to the database.
     * @param product The product to be saved.
     * @return The saved product.
     */
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Updates an existing product in the database.
     * @param productId The ID of the product to be updated.
     * @param productDetails The updated product details.
     * @return The updated product.
     * @throws RuntimeException if the product with the given ID is not found.
     */
    public Product updateProduct(Integer productId, Product productDetails) {
        Product product = productRepository.findById(productId)
                          .orElseThrow(() -> new RuntimeException("Product not found"));
                          
        product.setBrand(productDetails.getBrand());
        product.setType(productDetails.getType());
        product.setDescription(productDetails.getDescription());
        product.setCategory(productDetails.getCategory());

        return productRepository.save(product);
    }

    /**
     * Retrieves a product by its ID.
     * @param productId The ID of the product.
     * @return An Optional containing the product, or an empty Optional if the product is not found.
     */
    public Optional<Product> getProductById(Integer productId) {
        return productRepository.findById(productId);
    }

    /**
     * Retrieves all products from the database.
     * @return A list of all products.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a list of products with the given brand.
     * @param brand The brand of the products.
     * @return A list of products with the given brand.
     */
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    /**
     * Adds a new product to the database with the given details.
     * @param brand The brand of the product.
     * @param type The type of the product.
     * @param description The description of the product.
     * @param category The category of the product.
     * @return The saved product.
     */
    public Product addProduct(String brand, String type, String description, String category) {
        Product product = new Product();
        product.setBrand(brand);
        product.setType(type);
        product.setDescription(description);
        product.setCategory(category);
        return productRepository.save(product);
    }

    /**
     * Deletes a product from the database.
     * @param id The ID of the product to be deleted.
     */
    @Transactional             
    public void deleteProduct(Integer id) {
        cartItemRepository.deleteByProduct_Id(id);
        productRepository.deleteProductById(id);
    }

    /**
     * Retrieves a list of products with the given type.
     * @param type The type of the products.
     * @return A list of products with the given type.
     */
    public List<Product> getProductsByType(String type) {
        return productRepository.findByType(type);
    }

    /**
     * Retrieves a list of products with the given category.
     * @param category The category of the products.
     * @return A list of products with the given category.
     */
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    /**
     * Retrieves a list of all products with their price range.
     * @return A list of ProductDTO objects containing the products and their price range.
     */
    public List<ProductDTO> getAllProductsWithPrice() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            List<CartItem> cartItems = cartItemRepository.findByProduct_Id(product.getId());
            double minPrice = cartItems.stream().mapToDouble(CartItem::getPrice).min().orElse(0.0);
            double maxPrice = cartItems.stream().mapToDouble(CartItem::getPrice).max().orElse(0.0);

            ProductDTO dto = new ProductDTO(product, minPrice, maxPrice);
            productDTOs.add(dto);
        }
        return productDTOs;
    }

    /**
     * Retrieves a set of available colors for a product.
     * @param productId The ID of the product.
     * @return A set of available colors.
     */
    public Set<String> getAvailableColors(Integer productId) {
        return cartItemRepository.findByProduct_Id(productId)
                           .stream()
                           .map(CartItem::getColor)
                           .collect(Collectors.toSet());
    }

    /**
     * Retrieves a set of available sizes for a product.
     * @param productId The ID of the product.
     * @return A set of available sizes.
     */
    public Set<Double> getAvailableSizes(Integer productId) {
        return cartItemRepository.findByProduct_Id(productId)
                           .stream()
                           .map(CartItem::getSize)
                           .collect(Collectors.toSet());
    }

    /**
     * Retrieves the price range for a product.
     * @param productId The ID of the product.
     * @return An Object array containing the minimum and maximum prices.
     */
    public Object getPriceRange(Integer productId) {
        List<CartItem> cartItems = cartItemRepository.findByProduct_Id(productId);
        double minPrice = cartItems.stream().mapToDouble(CartItem::getPrice).min().orElse(0.0);
        double maxPrice = cartItems.stream().mapToDouble(CartItem::getPrice).max().orElse(0.0);

        return new Object[]{minPrice, maxPrice};
    }

	public Product findById(Integer productId) {
		return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
	}
}
