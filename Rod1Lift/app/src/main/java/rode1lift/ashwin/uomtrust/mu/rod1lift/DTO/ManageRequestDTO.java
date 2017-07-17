package rode1lift.ashwin.uomtrust.mu.rod1lift.DTO;

import java.io.Serializable;
import java.util.Date;

import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class ManageRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer manageRequestId;
    private Integer accountId;
    private Integer carId;
    private Integer requestId;
    private Integer seatRequested;

    private Date dateCreated;
    private Date dateUpdated;

    private RequestStatus requestStatus;

    public Integer getManageRequestId() {
        return manageRequestId;
    }

    public void setManageRequestId(Integer manageRequestId) {
        this.manageRequestId = manageRequestId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getSeatRequested() {
        return seatRequested;
    }

    public void setSeatRequested(Integer seatRequested) {
        this.seatRequested = seatRequested;
    }
}
