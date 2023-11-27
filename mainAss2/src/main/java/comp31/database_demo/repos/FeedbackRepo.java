package comp31.database_demo.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import comp31.database_demo.model.Feedback;
import comp31.database_demo.model.User;

/***
 * FeedbackRepo is an interface that extends CrudRepository
/**
 * This interface extends the CrudRepository interface and provides methods for interacting with the Feedback entity in the database.
 */

public interface FeedbackRepo extends CrudRepository<Feedback, Integer>{
    /**
     * Retrieves a list of Feedback objects by user ID.
     * @param userId The ID of the user.
     * @return A list of Feedback objects.
     */
    List<Feedback> findByUserId(User userId);

    /**
     * Retrieves a list of Feedback objects by product ID.
     * @param productId The ID of the product.
     * @return A list of Feedback objects.
     */
    List<Feedback> findByProductId(Integer productId);

    /**
     * Retrieves a list of Feedback objects by user ID and product ID.
     * @param userId The ID of the user.
     * @param productId The ID of the product.
     * @return A list of Feedback objects.
     */
    List<Feedback> findByUserIdAndProductId(Integer userId, Integer productId);

    /**
     * Retrieves a list of Feedback objects by rating.
     * @param rating The rating value.
     * @return A list of Feedback objects.
     */
    List<Feedback> findByRating(Integer rating);

    /**
     * Deletes Feedback objects by product ID.
     * @param productId The ID of the product.
     */
    void deleteByProductId(Integer productId);

    /**
     * Retrieves the average rating for a product by product ID.
     * @param productId The ID of the product.
     * @return The average rating as a Double value.
     */
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.product.id = :productId")
    Double findAverageRatingByProductId(@Param("productId") Integer productId);

    /**
     * Retrieves a list of Feedback objects by user ID and minimum rating.
     * @param userId The ID of the user.
     * @param minRating The minimum rating value.
     * @return A list of Feedback objects.
     */
    @Query("SELECT f FROM Feedback f WHERE f.user.id = :userId AND f.rating >= :minRating")
    List<Feedback> findByUserIdAndMinimumRating(@Param("userId") Integer userId, @Param("minRating") Integer minRating);
    
    /**
     * Counts the number of Feedback objects by product ID.
     * @param productId The ID of the product.
     * @return The count of Feedback objects.
     */
    int countByProductId(Integer productId);

    /**
     * Retrieves a list of Feedback objects with ratings between a minimum and maximum value.
     * @param minRating The minimum rating value.
     * @param maxRating The maximum rating value.
     * @return A list of Feedback objects.
     */
    List<Feedback> findByRatingBetween(Integer minRating, Integer maxRating);

}
