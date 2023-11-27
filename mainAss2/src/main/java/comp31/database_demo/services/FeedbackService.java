package comp31.database_demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import comp31.database_demo.model.Feedback;
import comp31.database_demo.model.User;
import comp31.database_demo.repos.FeedbackRepo;

import java.util.List;
import java.util.Optional;

/***
 * FeedbackService is a service class that handles the business logic for the Feedback model
 * 
 */

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepo feedbackRepo;
    
    public FeedbackService(FeedbackRepo feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    /**
     * Saves the given feedback in the database.
     * 
     * @param feedback The feedback to be saved.
     * @return The saved feedback.
     */
    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepo.save(feedback);        
    }

    /**
     * Retrieves the feedback with the given ID from the database.
     * 
     * @param id The ID of the feedback to be retrieved.
     * @return An Optional containing the feedback, or an empty Optional if no feedback is found.
     */
    public Optional<Feedback> getFeedbackById(Integer id) {
        return feedbackRepo.findById(id);
    }

    /**
     * Retrieves all feedbacks associated with the given user from the database.
     * 
     * @param userId The user whose feedbacks are to be retrieved.
     * @return A list of feedbacks associated with the user.
     */
    public List<Feedback> getFeedbackByUserId(User userId) {
        return feedbackRepo.findByUserId(userId);
    }

    /**
     * Retrieves all feedbacks associated with the given product ID from the database.
     * 
     * @param productId The ID of the product whose feedbacks are to be retrieved.
     * @return A list of feedbacks associated with the product.
     */
    public List<Feedback> getFeedbackByProductId(Integer productId) {
        return feedbackRepo.findByProductId(productId);
    }

    /**
     * Deletes the feedback with the given ID from the database.
     * 
     * @param feedbackId The ID of the feedback to be deleted.
     */
    public void deleteFeedback(Integer feedbackId) {
        feedbackRepo.deleteById(feedbackId);
    }

    /**
     * Calculates and returns the average rating for the given product.
     * 
     * @param productId The ID of the product.
     * @return The average rating for the product.
     */
    public Double getAverageRatingForProduct(Integer productId) {
        return feedbackRepo.findAverageRatingByProductId(productId);
    }

    /**
     * Updates the given feedback in the database.
     * 
     * @param feedback The feedback to be updated.
     */
    public void updateFeedback(Feedback feedback) {
        feedbackRepo.save(feedback);
    }

    /**
     * Retrieves all feedbacks associated with the given brand from the database.
     * 
     * @param brand The brand of the products whose feedbacks are to be retrieved.
     * @return A list of feedbacks associated with the brand.
     */
    public List<Feedback> getFeedbackByBrand(String brand) {
        return null;
    }

    /**
     * Deletes all feedbacks associated with the given product ID from the database.
     * 
     * @param productId The ID of the product whose feedbacks are to be deleted.
     */
	public void deleteByProductId(Integer productId) {
        feedbackRepo.deleteByProductId(productId);
    }
}
