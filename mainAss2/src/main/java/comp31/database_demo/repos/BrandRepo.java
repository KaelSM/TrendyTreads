package comp31.database_demo.repos;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comp31.database_demo.model.Brand;

@Repository
public interface BrandRepo extends JpaRepository<Brand, Long> {
    
    List<Brand> findByName(String name);
    
}