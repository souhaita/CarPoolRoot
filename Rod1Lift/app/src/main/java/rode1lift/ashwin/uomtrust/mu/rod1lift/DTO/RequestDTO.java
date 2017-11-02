package rode1lift.ashwin.uomtrust.mu.rod1lift.DTO;

import java.io.Serializable;
import java.util.Date;

import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class RequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer requestId;
    private Integer carId;
    private Integer price;
    private Integer accountId;
    private Integer seatAvailable;
    private Integer manageRequestId;
    private Integer seatRequested;

    private Date dateCreated;
    private Date dateUpdated;
    private Date eventDate;
    private Date tripEndTime;

    private String placeFrom;
    private String placeTo;
    private String driverName;

    private RequestStatus requestStatus;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getPlaceFrom() {
        return placeFrom;
    }

    public void setPlaceFrom(String placeFrom) {
        this.placeFrom = placeFrom;
    }

    public String getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(String placeTo) {
        this.placeTo = placeTo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getSeatAvailable() {
        return seatAvailable;
    }

    public void setSeatAvailable(Integer seatAvailable) {
        this.seatAvailable = seatAvailable;
    }

    public Integer getManageRequestId() {
        return manageRequestId;
    }

    public void setManageRequestId(Integer manageRequestId) {
        this.manageRequestId = manageRequestId;
    }

    public Integer getSeatRequested() {
        return seatRequested;
    }

    public void setSeatRequested(Integer seatRequested) {
        this.seatRequested = seatRequested;
    }

    public Date getTripEndTime() {
        return tripEndTime;
    }

    public void setTripEndTime(Date tripEndTime) {
        this.tripEndTime = tripEndTime;
    }
}
