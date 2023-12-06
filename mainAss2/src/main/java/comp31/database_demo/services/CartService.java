package comp31.database_demo.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import comp31.database_demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comp31.database_demo.model.Cart;
import comp31.database_demo.model.Product;
import comp31.database_demo.repos.CartRepo;
import comp31.database_demo.repos.ProductRepo;
import comp31.database_demo.repos.UserRepo;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    // Add a product to the cart
    public void addProductToCart(Long userId, Long productId, int quantity) {
        Optional<Cart> cartOpt = cartRepo.findByUserId(userId);
        Cart cart = cartOpt.orElseGet(() -> createNewCart(userId));

        Optional<Product> productOpt = productRepo.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            Map<Product, Integer> products = cart.getProducts();
            products.put(product, products.getOrDefault(product, 0) + quantity);
            cart.setProducts(products);
            cartRepo.save(cart);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }

    // Retrieve the cart for a specific user
    public Cart getUserCart(Long userId) {
        return cartRepo.findByUserId(userId).orElseGet(() -> createNewCart(userId));
    }

    // Create a new cart for the user
    private Cart createNewCart(Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isPresent()) {
            Cart newCart = new Cart();
            newCart.setUser(userOpt.get());
            newCart.setProducts(new HashMap<>()); // Initialize an empty products map
            return newCart;
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

        // Update the quantity of a product in the cart
        public void updateProductQuantity(Long userId, Long productId, int quantity) {
            Optional<Cart> cartOpt = cartRepo.findByUserId(userId);
            if (cartOpt.isPresent()) {
                Cart cart = cartOpt.get();
                Map<Product, Integer> products = cart.getProducts();
                Optional<Product> productOpt = productRepo.findById(productId);
                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    products.put(product, quantity);
                    cart.setProducts(products);
                    cartRepo.save(cart);
                } else {
                    throw new IllegalArgumentException("Product not found");
                }
            } else {
                throw new IllegalArgumentException("Cart not found");
            }
        }

        // Remove a product from the cart
        public void removeProductFromCart(Long userId, Long productId) {
            Optional<Cart> cartOpt = cartRepo.findByUserId(userId);
            if (cartOpt.isPresent()) {
                Cart cart = cartOpt.get();
                Map<Product, Integer> products = cart.getProducts();
                Optional<Product> productOpt = productRepo.findById(productId);
                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    products.remove(product);
                    cart.setProducts(products);
                    cartRepo.save(cart);
                } else {
                    throw new IllegalArgumentException("Product not found");
                }
            } else {
                throw new IllegalArgumentException("Cart not found");
            }
        }

        // Clear the cart
        public void clearCart(Long userId) {
            Optional<Cart> cartOpt = cartRepo.findByUserId(userId);
            if (cartOpt.isPresent()) {
                Cart cart = cartOpt.get();
                cart.getProducts().clear();
                cartRepo.save(cart);
            } else {
                throw new IllegalArgumentException("Cart not found");
            }
        }

        public Cart save(Cart cart) {
            return cartRepo.save(cart); // Assuming cartRepo is your JPA repository
        }
    

        public double calculateTotalAmount(Cart cart) {
        double totalAmount = 0;
        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double productTotal = product.getPrice() * quantity;

            // Debugging output
            System.out.println("Product: " + product.getName() + ", Quantity: " + quantity + ", Total: " + productTotal);

            totalAmount += productTotal;
        }
        return totalAmount;
        }
    }
