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

/**
 * This class represents a service for managing user carts.
 * It provides methods for adding, updating, and removing products from the cart,
 * as well as calculating the cart total amount.
 *
 * @param cartRepo   The repository for accessing cart data.
 * @param userRepo   The repository for accessing user data.
 * @param productRepo The repository for accessing product data.
 */
@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    /**
     * Adds a product to the user's cart with the specified quantity.
     * If the user's cart does not exist, a new cart will be created.
     * 
     * @param userId    the ID of the user
     * @param productId the ID of the product to be added
     * @param quantity  the quantity of the product to be added
     * @throws IllegalArgumentException if the product is not found
     */
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


    /**
     * Retrieves the cart for the specified user. If the user has a cart in the database, it is returned.
     * Otherwise, a new cart is created and returned.
     *
     * @param userId the ID of the user
     * @return the user's cart
     */
    public Cart getUserCart(Long userId) {
        return cartRepo.findByUserId(userId).orElseGet(() -> createNewCart(userId));
    }


    /**
     * Creates a new cart for the specified user.
     *
     * @param userId the ID of the user
     * @return the new cart
     */
    private Cart createNewCart(Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isPresent()) {
            Cart newCart = new Cart();
            newCart.setUser(userOpt.get());
            newCart.setProducts(new HashMap<>()); 
            return newCart;
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    /**
     * Updates the quantity of a product in the user's cart.
     *  
     * @param userId    the ID of the user
     * @param productId the ID of the product to be updated
     * @param quantity  the new quantity of the product
     * @throws IllegalArgumentException if the product is not found
     * @throws IllegalArgumentException if the cart is not found
     */
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

        /*
        * Removes a product from the user's cart.
        * 
        * @param userId    the ID of the user
        * @param productId the ID of the product to be removed
        * @throws IllegalArgumentException if the product is not found
        * 
        * @throws IllegalArgumentException if the cart is not found
        */
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

        /* 
        * Clears the user's cart.
        * 
        * @param userId the ID of the user
        * @throws IllegalArgumentException if the cart is not found
        * 
        */
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

        /*
        * Calculates the total amount of the user's cart.
        *
        * @param userId the ID of the user
        * @return the total amount of the user's cart
        */
        public Cart save(Cart cart) {
            return cartRepo.save(cart); 
        }
    
        /**
         * Updates the quantity of a product in the user's cart and recalculates the total cart amount.
         * 
         * @param userId    the ID of the user
         * @param productId the ID of the product
         * @param quantity  the new quantity of the product
         */
        public void updateProductQuantityAndCalculateTotal(Long userId, Long productId, int quantity) {
            Cart cart = getUserCart(userId);
            updateProductQuantity(cart, productId, quantity);
            calculateAndSetCartTotal(cart);
        }
    

        /**
         * Updates the quantity of a product in the cart.
         *
         * @param cart the cart object
         * @param productId the ID of the product to update
         * @param quantity the new quantity of the product
         */
        private void updateProductQuantity(Cart cart, Long productId, int quantity) {
            Map<Product, Integer> products = cart.getProducts();
            Product product = productRepo.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
            if (product.getStock() < quantity) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }
    
            products.put(product, quantity);
            cart.setProducts(products);
            cartRepo.save(cart);
        }

        /**
         * Calculates the total amount of the cart and sets it.
         *
         * @param cart the cart object
         */
    
        private void calculateAndSetCartTotal(Cart cart) {
            double subtotal = calculateSubtotal(cart.getProducts());
            double tax = calculateTax(subtotal);
            double total = subtotal + tax;
            cart.setTotalAmount(total);
            cartRepo.save(cart);
        }
    
        /**
         * Calculates the subtotal of the cart.
         *
         * @param products the products in the cart
         * @return the subtotal of the cart
         */


        public double calculateSubtotal(Map<Product, Integer> products) {
            double subtotal = 0;
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                Integer quantity = entry.getValue();
                subtotal += product.getPrice() * quantity;
            }
            return subtotal;
        }

        /**
         * Calculates the tax of the subtotal.
         *
         * @param subtotal the subtotal of the cart
         * @return the tax of the subtotal
         */
    
        public double calculateTax(double subtotal) {
            final double TAX_RATE = 0.13; 
            return subtotal * TAX_RATE;
        }
    
    }
