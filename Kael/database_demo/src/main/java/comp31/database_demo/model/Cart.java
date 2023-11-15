package comp31.database_demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Assuming each cart is related to a single user
    // You can add a user field here if needed, e.g., private User user;
    // Ensure that you have a User entity defined as well

    @OneToMany(mappedBy = "cart")
    private List<Product> products = new ArrayList<>();

    // Add methods to manipulate the cart, like addProduct, removeProduct, etc.

    // Method to add a product to the cart
    public void addProduct(Product product) {
        this.products.add(product);
    }

    // Method to remove a product from the cart
    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    // Method to get the total price of the cart
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Product product : this.products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    // Method to get the number of products in the cart
    public int getNumProducts() {
        return this.products.size();
    }

    // Method to get the number of unique products in the cart
    public int getNumUniqueProducts() {
        return this.products.size();
    }

    //method to get the number of times a product has been added to a cart
    public int getNumTimesProductAddedToCart(Product product) {
        int numTimesProductAddedToCart = 0;
        for (Product productInCart : this.products) {
            if (productInCart.getId() == product.getId()) {
                numTimesProductAddedToCart++;
            }
        }
        return numTimesProductAddedToCart;
    }

    //method to find how many items with the same brand there are
    public int getNumProductsByBrand(String brand) {
        int numProductsByBrand = 0;
        for (Product productInCart : this.products) {
            if (productInCart.getBrand().equals(brand)) {
                numProductsByBrand++;
            }
        }
        return numProductsByBrand;
    }
}

