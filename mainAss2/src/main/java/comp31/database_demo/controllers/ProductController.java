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
    // Add Product
    @PostMapping("/addProduct")
    public String addProduct(@RequestParam String name, @RequestParam double price, @RequestParam int stock, @RequestParam Long brandId, RedirectAttributes redirectAttributes) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        Brand brand = brandService.getBrandById(brandId);
        product.setBrand(brand);
        productService.saveProduct(product, brandId); // Ensure this method saves the product and sets the brand correctly (see ProductService.java
        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        return "redirect:/management";
    }

    


    // Update Product
    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable Long id, @RequestParam String newName, @RequestParam double newPrice, @RequestParam int newStock, @RequestParam Long brandId, RedirectAttributes redirectAttributes) {
    try {
        Product product = productService.getProductById(id);
        product.setName(newName);
        product.setPrice(newPrice);
        product.setStock(newStock);
        productService.updateProduct(id, product, brandId); // Assuming this method updates the product and sets the brand correctly
        redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Error updating product");
    }
    return "redirect:/management";
    }

    

    // Delete Product
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

    // View Products
    @GetMapping("/product")
    public String showProductsPage(Model model) {
        model.addAttribute("product", productService.getAllProducts());
        return "product"; // The name of your products view template
    }

    @GetMapping("/productDetails/{id}")
    public String showProductDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new IllegalArgumentException("Invalid product ID: " + id);
        }
        model.addAttribute("product", product);

        // The return statement here constructs the view name dynamically based on the product ID.
        // For example, if the product ID is 1, it will return "1" which should match "1.html" in the templates directory.
        return "productDetails"; // Just convert the ID to a string to match the template name
    }



}