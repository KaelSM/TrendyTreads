package comp31.a2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import comp31.a2.model.entities.Feedback;
import comp31.a2.model.entities.User;
import comp31.a2.model.repositories.FeedbackRepository;
import comp31.a2.model.repositories.UserRepository;
import comp31.a2.services.FeedbackService;
import java.util.Optional;



    @Controller
    @RequestMapping("/feedback")
    public class FeedbackController {

        @Autowired
        private FeedbackService feedbackService;

        @Autowired
        private UserRepository userRepository;

        @GetMapping("/user/{userId}")
        public String viewFeedbackByUser(@PathVariable Long userId, Model model) {
            List<Feedback> feedbacks = feedbackService.findByUserId(userId);
            model.addAttribute("feedbacks", feedbacks);
            return "feedbackView"; // Thymeleaf template name
        }

        @PostMapping("/submit")
        public String submitFeedback(@RequestParam String feedbackMessage, @RequestParam Long userId) {
            Optional<User> optionalUser = (Optional<User>) userRepository.findById(userId);
            User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
            Feedback feedback = new Feedback(feedbackMessage, user);
            feedbackService.saveFeedback(feedback);
            return "redirect:/feedback/user/" + userId;
        }
    }
