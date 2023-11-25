package comp31.database_demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import comp31.database_demo.model.Feedback;
import comp31.database_demo.repos.FeedbackRepo;

import java.util.List;
import java.util.Optional;

/***
 * FeedbackService is a service class that handles the business logic for the Feedback model
 * @param getFeedbackByUserId(Integer userId) returns a list of all feedback with the given userId
 * @param saveFeedback(Feedback feedback) saves the given feedback
 */

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepo feedbackRepo;
    
    public FeedbackService(FeedbackRepo feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepo.save(feedback);        
    }

    public Optional<Feedback> getFeedbackById(Integer id) {
        return feedbackRepo.findById(id);
    }

    public List<Feedback> getFeedbackByUserId(Integer userId) {
        return feedbackRepo.findByUserId(userId);
    }

    public List<Feedback> getFeedbackByProductId(Integer productId) {
        return feedbackRepo.findByProductId(productId);
    }

    public void deleteFeedback(Integer feedbackId) {
        feedbackRepo.deleteById(feedbackId);
    }

    public Double getAverageRatingForProduct(Integer productId) {
        return feedbackRepo.findAverageRatingByProductId(productId);
    }

    public void updateFeedback(Feedback feedback) {
        feedbackRepo.save(feedback);
    }
}
