package comp31.a2.model;

public class FeedbackDto {
    private String feedbackMessage;
    private Long userId;


    public String getFeedbackMessage() {
        return feedbackMessage;
    }
    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
