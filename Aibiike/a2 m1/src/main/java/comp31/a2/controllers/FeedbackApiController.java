package comp31.a2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comp31.a2.model.FeedbackDto;
import comp31.a2.model.entities.Feedback;
import comp31.a2.model.entities.User;
import comp31.a2.model.repositories.UserRepository;
import comp31.a2.services.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackApiController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbackByUser(@PathVariable Long userId) {
        List<Feedback> feedbacks = feedbackService.findByUserId(userId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbacks = feedbackService.findAll();
        return ResponseEntity.ok(feedbacks);
    }

    @PostMapping("/submit")
    public ResponseEntity<Feedback> submitFeedback(@RequestBody FeedbackDto feedbackDto) {
        User user = userRepository.findById(feedbackDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Feedback feedback = new Feedback(feedbackDto.getFeedbackMessage(), user);
        feedbackService.save(feedback);
        return ResponseEntity.ok(feedback);
    }
}