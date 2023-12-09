package comp31.database_demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import comp31.database_demo.model.Brand;
import comp31.database_demo.model.Product;
import comp31.database_demo.services.ProductService;
import comp31.database_demo.services.BrandService;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    BrandService brandService;

    /**
     * Endpoint to add a new product.
     * 
     * @param name               The name of the product.
     * @param price              The price of the product.
     * @param stock              The stock quantity of the product.
     * @param brandId            The ID of the brand associated with the product.
     * @param redirectAttributes The redirect attributes for flash messages.
     * @return The redirect URL after adding the product.
     */
    @PostMapping("/addProduct")
    public String addProduct(@RequestParam String name, @RequestParam double price, @RequestParam int stock, @RequestParam Long brandId, RedirectAttributes redirectAttributes) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        Brand brand = brandService.getBrandById(brandId);
        product.setBrand(brand);
        productService.saveProduct(product, brandId); 
        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        return "redirect:/management";
    }

    /**
     * Endpoint to update an existing product.
     * 
     * @param id                 The ID of the product to update.
     * @param newName            The new name of the product.
     * @param newPrice           The new price of the product.
     * @param newStock           The new stock quantity of the product.
     * @param brandId            The ID of the brand associated with the product.
     * @param redirectAttributes The redirect attributes for flash messages.
     * @return The redirect URL after updating the product.
     */
    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable Long id, @RequestParam String newName, @RequestParam double newPrice, @RequestParam int newStock, @RequestParam Long brandId, RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.getProductById(id);
            product.setName(newName);
            product.setPrice(newPrice);
            product.setStock(newStock);
            productService.updateProduct(id, product, brandId); 
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating product");
        }
        return "redirect:/management";
    }

    /**
     * Endpoint to delete a product.
     * 
     * @param id                 The ID of the product to delete.
     * @param redirectAttributes The redirect attributes for flash messages.
     * @return The redirect URL after deleting the product.
     */
    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting product");
        }
        return "redirect:/management";
    }

    /**
     * Endpoint to display all products.
     * 
     * @param model The model to add the products to.
     * @return The view name for displaying all products.
     */
    @GetMapping("/product")
    public String showProductsPage(Model model) {
        model.addAttribute("product", productService.getAllProducts());
        return "product"; 
    }

    /**
     * Endpoint to display product details.
     * 
     * @param id    The ID of the product to display details for.
     * @param model The model to add the product details to.
     * @return The view name for displaying product details.
     * @throws IllegalArgumentException If the product ID is invalid.
     */
    @GetMapping("/productDetails/{id}")
    public String showProductDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new IllegalArgumentException("Invalid product ID: " + id);
        }
        model.addAttribute("product", product);
        return "productDetails"; 
    }
}