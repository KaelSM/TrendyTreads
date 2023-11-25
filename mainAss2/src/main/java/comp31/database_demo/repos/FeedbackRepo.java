package comp31.database_demo.repos;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

import comp31.database_demo.model.Feedback;

/***
 * FeedbackRepo is an interface that extends CrudRepository
 *  @param findAll() returns a list of all feedback
 * @param findByUserId(Integer userId) returns a list of all feedback with the given userId
 * @param findByProductId(Integer productId) returns a list of all feedback with the given productId
 * @param findByUserIdAndProductId(Integer userId, Integer productId) returns a list of all feedback with the given userId and productId
 * @param findByRating(Integer rating) returns a list of all feedback with the given rating
 * @param updateFeedback(Feedback feedback) updates the feedback with the given feedback
 * 
 *  
 * */

public interface FeedbackRepo extends CrudRepository<Feedback, Integer>{
    List<Feedback> findByUserId(Integer userId);
    List<Feedback> findByProductId(Integer productId);
    List<Feedback> findByUserIdAndProductId(Integer userId, Integer productId);
    List<Feedback> findByRating(Integer rating);

    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.product.id = :productId")
    Double findAverageRatingByProductId(@Param("productId") Integer productId);

    @Query("SELECT f FROM Feedback f WHERE f.user.id = :userId AND f.rating >= :minRating")
    List<Feedback> findByUserIdAndMinimumRating(@Param("userId") Integer userId, @Param("minRating") Integer minRating);
    
     @Query("SELECT f FROM Feedback f ORDER BY f.createdAt DESC")
    List<Feedback> findRecentFeedback();

    int countByProductId(Integer productId);

    List<Feedback> findByRatingBetween(Integer minRating, Integer maxRating);

    @Query("DELETE FROM Feedback f WHERE f.createdAt < :cutoffDate")
    void deleteFeedbackOlderThan(@Param("cutoffDate") LocalDate cutoffDate);

    @Query("SELECT f FROM Feedback f WHERE f.feedbackMessage LIKE %:keyword%")
    List<Feedback> findByKeywordInMessage(@Param("keyword") String keyword);
}
