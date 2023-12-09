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
import comp31.database_demo.services.BrandService;

/**
 * The BrandController class handles the HTTP requests related to brands.
 */
@Controller
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * Adds a new brand to the database.
     * 
     * @param name                the name of the brand
     * @param redirectAttributes  the redirect attributes for flash messages
     * @return                    the redirect path to the management page
     */
    @PostMapping("/addBrand")
    public String addBrand(@RequestParam String name, RedirectAttributes redirectAttributes) {
        Brand brand = new Brand();
        brand.setName(name);
        brandService.saveBrand(brand);
        redirectAttributes.addFlashAttribute("successMessage", "Brand added successfully!");
        return "redirect:/management";
    }

    /**
     * Displays the update brand form for a specific brand.
     * 
     * @param id      the ID of the brand to update
     * @param model   the model to add the brand object
     * @return        the view name for the update brand form
     */
    @GetMapping("/updateBrand/{id}")
    public String showUpdateBrandForm(@PathVariable Long id, Model model) {
        Brand brand = brandService.getBrandById(id);
        model.addAttribute("brand", brand);
        return "updateBrandForm";
    }

    /**
     * Updates the name of a specific brand.
     * 
     * @param id                    the ID of the brand to update
     * @param newName               the new name for the brand
     * @param redirectAttributes    the redirect attributes for flash messages
     * @return                      the redirect path to the management page
     */
    @PostMapping("/updateBrand/{id}")
    public String updateBrand(@PathVariable Long id, @RequestParam String newName, RedirectAttributes redirectAttributes) {
        try {
            Brand brand = brandService.getBrandById(id);
            brand.setName(newName);
            brandService.saveBrand(brand);
            redirectAttributes.addFlashAttribute("successMessage", "Brand updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating brand");
        }
        return "redirect:/management";
    }

    /**
     * Deletes a specific brand and its associated products.
     * 
     * @param id                    the ID of the brand to delete
     * @param redirectAttributes    the redirect attributes for flash messages
     * @return                      the redirect path to the management page
     */
    @PostMapping("/deleteBrand")
    public String deleteBrand(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            brandService.deleteBrand(id);
            redirectAttributes.addFlashAttribute("successMessage", "Brand and associated products deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting brand");
        }
        return "redirect:/management";
    }
}