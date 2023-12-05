package comp31.database_demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import comp31.database_demo.model.Brand;
import comp31.database_demo.model.Product;
import comp31.database_demo.repos.BrandRepo;
import comp31.database_demo.repos.ProductRepo;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepo brandRepository;

    @Autowired
    private ProductRepo productRepository;

    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
    }

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand updateBrand(Long id, String newName) {
        Brand brand = brandRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        brand.setName(newName);
        return brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

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