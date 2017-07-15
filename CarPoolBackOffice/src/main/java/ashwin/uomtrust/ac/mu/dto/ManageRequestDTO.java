package ashwin.uomtrust.ac.mu.dto;

import java.io.Serializable;
import java.util.Date;

import ashwin.uomtrust.ac.mu.enums.RequestStatus;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class ManageRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long manageRequestId;
    private Long accountId;
    private Long carId;
    private Long requestId;

    private Date dateCreated;
    private Date dateUpdated;

    private RequestStatus requestStatus;

    public Long getManageRequestId() {
        return manageRequestId;
    }

    public void setManageRequestId(Long manageRequestId) {
        this.manageRequestId = manageRequestId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
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
}
