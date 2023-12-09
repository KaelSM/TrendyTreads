package comp31.database_demo.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comp31.database_demo.model.Cart;
import comp31.database_demo.model.Checkout;
import comp31.database_demo.model.Product;
import comp31.database_demo.repos.CartRepo;
import comp31.database_demo.repos.CheckoutRepo;
import comp31.database_demo.repos.ProductRepo;
import jakarta.transaction.Transactional;

@Service
public class CheckoutService {

    @Autowired
    private CheckoutRepo checkoutRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    /**
     * Process the checkout for a given cart.
     * 
     * @param cartId        the ID of the cart to process the checkout for
     * @param name          the name of the customer
     * @param address       the address of the customer
     * @param email         the email of the customer
     * @param paymentMethod the payment method used for the checkout
     */
    @Transactional
    public void processCheckout(Long cartId, String name, String address, String email, String paymentMethod) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));

        Checkout checkout = new Checkout();
        checkout.setCart(cart);
        checkout.setName(name);
        checkout.setAddress(address);
        checkout.setEmail(email);
        checkout.setPaymentMethod(paymentMethod);
        checkoutRepo.save(checkout);
    }

    /**
     * Retrieves all checkouts.
     * 
     * @return A list of all checkouts.
     */

    public List<Checkout> listAll() {
        return checkoutRepo.findAll();
    }

    /**
     * Retrieves a checkout by its ID.
     * 
     * @param id The ID of the checkout to retrieve.
     * @return The checkout with the specified ID.
     */

    public Checkout saveCheckout(Checkout checkout) {
        return checkoutRepo.save(checkout);
    }

    /**
     * Retrieves a checkout by its ID.
     * 
     * @param id The ID of the checkout to retrieve.
     * @return The checkout with the specified ID.
     */

    public Checkout get(Long id) {
        return checkoutRepo.findById(id).get();
    }

    /**
     * Deletes a checkout by its ID.
     * 
     * @param id The ID of the checkout to delete.
     */

    public void delete(Long id) {
        checkoutRepo.deleteById(id);
    }

    /**
     * Finalizes the checkout for a given cart.
     * 
     * @param cart the cart to finalize the checkout for
     * @throws IllegalArgumentException if the cart is empty or if the quantity of
     *                                  any product is invalid or if the stock of any
     *                                  product is insufficient
     */

    public void finalizeCheckout(Cart cart) {
        if (cart.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            if (quantity <= 0) {
                throw new IllegalArgumentException("Invalid quantity for product: " + product.getName());
            }

            if (product.getStock() < quantity) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - quantity);
            productRepo.save(product);
        }
    }

    /**
     * Retrieves all checkouts.
     * 
     * @return A list of all checkouts.
     */

    public List<Checkout> getAllCheckouts() {
        return checkoutRepo.findAll();
    }

}
