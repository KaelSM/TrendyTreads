package comp31.a2.model.entities;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long feedbackID;

    @NotNull
    @Size(min = 1, max = 500)
    private String feedbackMessage;

    // Assuming a Customer entity exists with 'customerID' as its primary key
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User user;


    public Feedback(String feedbackMessage, User user) {
        this.feedbackMessage = feedbackMessage;
        this.user = user;
    }
}
