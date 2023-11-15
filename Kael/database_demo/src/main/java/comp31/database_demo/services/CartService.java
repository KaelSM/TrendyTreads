package comp31.database_demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comp31.database_demo.model.Cart;
import comp31.database_demo.model.Product;
import comp31.database_demo.repos.CartRepo;
import comp31.database_demo.repos.ProductRepo;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;

    public CartService(CartRepo cartRepo, ProductRepo productRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public void addProductToCart(Integer cartId, Integer productId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        cart.addProduct(product);
        cartRepo.save(cart);
    }

    @Transactional
    public void removeProductFromCart(Integer cartId, Integer productId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        cart.removeProduct(product);
        cartRepo.save(cart);
    }

    public Optional<Cart> findCartById(Integer cartId) {
        return cartRepo.findById(cartId);
    }

    public Iterable<Cart> findAllCarts() {
        return cartRepo.findAll();
    }

    public Iterable<Cart> findCartsByProductId(Integer productId) {
        return cartRepo.findByProductsId(productId);
    }
}