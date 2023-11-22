package comp31.database_demo.repos;

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
    List<Feedback> findAll();
    List<Feedback> findByUserId(Integer userId);
    List<Feedback> findByProductId(Integer productId);
    List<Feedback> findByUserIdAndProductId(Integer userId, Integer productId);
    List<Feedback> findByRating(Integer rating);
    Feedback updateFeedback( Feedback feedback);
}
