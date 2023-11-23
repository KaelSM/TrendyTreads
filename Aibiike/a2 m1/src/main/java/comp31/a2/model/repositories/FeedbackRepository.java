package comp31.a2.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import comp31.a2.model.entities.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // Example custom method: Find feedbacks by customer
    List<Feedback> findByCustomerId(Long customerId);
}
