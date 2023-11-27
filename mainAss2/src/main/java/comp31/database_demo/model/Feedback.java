package comp31.database_demo.model;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
    * Constructs a new Feedback with the specified feedback message.
    *
    * @param feedbackid      the feedback id
    * @param feedbackMessage the feedback message
    * @param user            the user who gave the feedback
    * @param product         the product that the feedback is for
    * @return a new Feedback with the specified feedback message
    *
    */

@Entity
@Data
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedbackId;
    @NotNull
    
    private Integer rating;
    private String feedbackMessage;

    @ManyToOne
    @JoinColumn(name = "user_id") // Foreign key column in Feedback table for User
    private User user; // Each feedback is associated with one user


    @ManyToOne
    @JoinColumn(name = "fkey_product") // foreign key column in Feedback table
    private Product product; // Each feedback is associated with one product
   
    public Feedback(String feedbackMessage, Integer rating, User user, Product product) {
        this.feedbackMessage = feedbackMessage;
        this.user = user;
        this.rating = rating;
        this.product = product;
    }

    /**
     * Gets the feedback message.
     *
     * @return the feedback message
     */
    public List<User> getUser(User userId) {
        List<User> user = new ArrayList<User>();
        return user;
        
    }

    /**
     * Gets the feedback message.
     *
     * @return the feedback message
     */
    public Integer getId() {
        return feedbackId;
    }

}
