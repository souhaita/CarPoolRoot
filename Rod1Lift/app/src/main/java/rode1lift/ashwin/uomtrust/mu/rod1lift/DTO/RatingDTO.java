package rode1lift.ashwin.uomtrust.mu.rod1lift.DTO;

import java.io.Serializable;
import java.util.Date;

import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class RatingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ratingId;
    private Integer requestId;
    private Integer accountId;
    private Integer carId;

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
}
