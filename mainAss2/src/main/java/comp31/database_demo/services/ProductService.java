package comp31.database_demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comp31.database_demo.model.CartItem;
import comp31.database_demo.model.Feedback;
import comp31.database_demo.model.Product;
import comp31.database_demo.repos.CartItemRepo;
import comp31.database_demo.repos.ProductRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * ProductService is a service class that handles the business logic for the Product model
 * @param getProductById(Integer id) returns the product with the given id
 * @param getProductsByBrand(String brand) returns a list of all products with the given brand
 * @param saveProduct(Product product) saves the given product
 * @param deleteProduct(Integer id) deletes the product with the given id
 * 
 */

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private CartItemRepo cartItemRepository;

    public ProductService(ProductRepo productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Integer productId, Product productDetails) {
        Product product = productRepository.findById(productId)
                          .orElseThrow(() -> new RuntimeException("Product not found"));
                          
        product.setBrand(productDetails.getBrand());
        product.setType(productDetails.getType());
        product.setDescription(productDetails.getDescription());
        product.setCategory(productDetails.getCategory());

        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    public Product addProduct(String brand, String type, String description, String category) {
        Product product = new Product();
        product.setBrand(brand);
        product.setType(type);
        product.setDescription(description);
        product.setCategory(category);
        return productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByType(String type) {
        return productRepository.findByType(type);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<ProductDTO> getAllProductsWithPrice() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            List<CartItem> cartItems = cartItemRepository.findByProductId(product.getId());
            double minPrice = cartItems.stream().mapToDouble(CartItem::getPrice).min().orElse(0.0);
            double maxPrice = cartItems.stream().mapToDouble(CartItem::getPrice).max().orElse(0.0);

            ProductDTO dto = new ProductDTO(product, minPrice, maxPrice);
            productDTOs.add(dto);
        }
        return productDTOs;
    }

     public Set<String> getAvailableColors(Integer productId) {
        return cartItemRepository.findByProductId(productId)
                           .stream()
                           .map(CartItem::getColor)
                           .collect(Collectors.toSet());
    }

    public Set<Double> getAvailableSizes(Integer productId) {
        return cartItemRepository.findByProductId(productId)
                           .stream()
                           .map(CartItem::getSize)
                           .collect(Collectors.toSet());
    }

    public Object getPriceRange(Integer productId) {
        List<CartItem> cartItems = cartItemRepository.findByProductId(productId);
        double minPrice = cartItems.stream().mapToDouble(CartItem::getPrice).min().orElse(0.0);
        double maxPrice = cartItems.stream().mapToDouble(CartItem::getPrice).max().orElse(0.0);

        return new Object[]{minPrice, maxPrice};
    }
}
