package rode1lift.ashwin.uomtrust.mu.rod1lift.DTO;

import java.io.Serializable;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class RatingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ratingId;
    private Integer requestId;
    private Integer accountId;
    private Integer raterId;
    private Integer carId;

    private String comment;

    private Double rating;

    public Integer getRatingId() {
        return ratingId;
    }

    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getRaterId() {
        return raterId;
    }

    public void setRaterId(Integer rater) {
        this.raterId = rater;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
