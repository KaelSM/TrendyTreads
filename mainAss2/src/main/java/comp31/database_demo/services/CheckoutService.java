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

    @Transactional
    public void processCheckout(Long cartId, String name, String address, String email, String paymentMethod, String shipmentMethod) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));
        double totalAmount = cart.getTotalAmount(); // Assuming this method exists and calculates total amount of the cart.
        double shippingCost = calculateShippingCost(shipmentMethod);

        Checkout checkout = new Checkout();
        checkout.setCart(cart);
        checkout.setName(name);
        checkout.setAddress(address);
        checkout.setEmail(email);
        cart.getTotalAmount();
        checkout.setPaymentMethod(paymentMethod);
        checkout.setShipmentMethod(shipmentMethod);
        checkout.setTotalAmount(totalAmount + shippingCost);
        
        checkoutRepo.save(checkout);
    }
    private double calculateShippingCost(String shipmentMethod) {
        double shippingCost = 0.0;
        switch (shipmentMethod) {
            case "standard":
                shippingCost = 20.0;
                break;
            case "express":
                shippingCost = 40.0;
                break;
            case "nextDay":
                shippingCost = 120.0;
                break;
            default:
                break;
        }
        return shippingCost;
    }

    public List<Checkout> listAll() {
        return checkoutRepo.findAll();
    }

    public Checkout saveCheckout(Checkout checkout) {
        return checkoutRepo.save(checkout);
    }

    public Checkout get(Long id) {
        return checkoutRepo.findById(id).get();
    }

    public void delete(Long id) {
        checkoutRepo.deleteById(id);
    }

    public void finalizeCheckout(Cart cart) {
        // Check if the cart is empty
        if (cart.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // Iterate over the products in the cart
        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            // Check if the requested quantity is valid
            if (quantity <= 0) {
                throw new IllegalArgumentException("Invalid quantity for product: " + product.getName());
            }
            
            // Check if there is enough stock for the product
            if (product.getStock() < quantity) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            // Deduct the quantity from the product's stock and save the updated product
            product.setStock(product.getStock() - quantity);
            productRepo.save(product);
        }

        // Additional checkout logic such as saving the checkout, clearing the cart, etc.
    }
    
}
