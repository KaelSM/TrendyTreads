package comp31.database_demo.services;


import org.springframework.stereotype.Service;
import comp31.database_demo.model.Feedback;
import comp31.database_demo.repos.FeedbackRepo;

import java.util.List;

/***
 * FeedbackService is a service class that handles the business logic for the Feedback model
 * @param getFeedbackByUserId(Integer userId) returns a list of all feedback with the given userId
 * @param saveFeedback(Feedback feedback) saves the given feedback
 * 
 */

@Service
public class FeedbackService {
    private final FeedbackRepo FeedbackRepo;

    
    public FeedbackService(FeedbackRepo FeedbackRepo) {
        this.FeedbackRepo = FeedbackRepo;
    }

    public List<Feedback> getFeedbackByUserId(Integer userId) {
        return FeedbackRepo.findByUserId(userId);
    }

    public Feedback saveFeedback(Feedback feedback) {
        return FeedbackRepo.save(feedback);
    }

   
}
