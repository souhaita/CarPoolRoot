package ashwin.uomtrust.ac.mu.dto;

import java.io.Serializable;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class RatingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ratingId;
	private Long requestId;
	private Long accountId;
	private Long raterId;
	private Long carId;

	private String comment;

	private Double rating;

	public Long getRatingId() {
		return ratingId;
	}

	public void setRatingId(Long ratingId) {
		this.ratingId = ratingId;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getRaterId() {
		return raterId;
	}

	public void setRaterId(Long raterId) {
		this.raterId = raterId;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
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
