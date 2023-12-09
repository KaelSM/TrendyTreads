package comp31.database_demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import comp31.database_demo.model.Brand;
import comp31.database_demo.model.Product;
import comp31.database_demo.repos.BrandRepo;
import comp31.database_demo.repos.ProductRepo;

import java.util.List;

/**
 * This class represents the service layer for managing brands.
 */
@Service
public class BrandService {

    @Autowired
    private BrandRepo brandRepository;

    @Autowired
    private ProductRepo productRepository;

    /**
     * Retrieves a brand by its ID.
     *
     * @param id The ID of the brand to retrieve.
     * @return The brand with the specified ID.
     * @throws RuntimeException if the brand is not found.
     */
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
    }

    /**
     * Saves a brand.
     *
     * @param brand The brand to save.
     * @return The saved brand.
     */
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    /**
     * Updates the name of a brand.
     *
     * @param id      The ID of the brand to update.
     * @param newName The new name for the brand.
     * @return The updated brand.
     * @throws RuntimeException if the brand is not found.
     */
    public Brand updateBrand(Long id, String newName) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        brand.setName(newName);
        return brandRepository.save(brand);
    }

    /**
     * Deletes a brand by its ID.
     *
     * @param id The ID of the brand to delete.
     */
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    /**
     * Retrieves all brands.
     *
     * @return A list of all brands.
     */
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    /**
     * Deletes a brand and all its related products.
     *
     * @param brandId The ID of the brand to delete.
     * @throws RuntimeException if the brand is not found.
     */
    public void deleteBrandAndRelatedProducts(Long brandId) {
        // Fetch the brand first
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + brandId));

        // Delete all products associated with the brand
        List<Product> products = productRepository.findAllByBrand(brand);
        for (Product product : products) {
            productRepository.delete(product);
        }

        // Finally, delete the brand itself
        brandRepository.delete(brand);
    }
}