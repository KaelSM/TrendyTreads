package comp31.database_demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comp31.database_demo.model.Cart;
import comp31.database_demo.model.Checkout;
import comp31.database_demo.repos.CartRepo;
import comp31.database_demo.repos.CheckoutRepo;
import jakarta.transaction.Transactional;

@Service
public class CheckoutService {

    @Autowired
    private CheckoutRepo checkoutRepo;

    @Autowired
    private CartRepo cartRepo;

    @Transactional
    public void processCheckout(Long cartId, String name, String address, String email, String phone, String country, int paymentMethod) {
        // Retrieve the cart based on cartId
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));

        // You can perform stock checks or updates here if necessary

        // Create a new Checkout instance and set its properties
        Checkout checkout = new Checkout();
        checkout.setCart(cart);
        checkout.setName(name);
        checkout.setAddress(address);
        checkout.setEmail(email);
        checkout.setPhone(phone);
        checkout.setCountry(country);
        checkout.setPayMethord(paymentMethod); 
        // Save the checkout details
        checkoutRepo.save(checkout);
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
    
}
