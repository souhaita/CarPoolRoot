package ashwin.uomtrust.ac.mu.dto;

import java.io.Serializable;
import java.util.Date;

import ashwin.uomtrust.ac.mu.enums.RequestStatus;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class RequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long requestId;
    private Long carId;
    private Long accountId;
    private Long manageRequestId;

    private Integer price;
    private Integer seatAvailable;
    private Integer seatRequested;

    private Date dateCreated;
    private Date dateUpdated;
    private Date eventDate;
    private Date tripDuration;

    private String placeFrom;
    private String placeTo;
    private String driverName;

    private RequestStatus requestStatus;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
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

    public void setEventDate(Date evenDate) {
        this.eventDate = evenDate;
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

	public Long getManageRequestId() {
		return manageRequestId;
	}

	public void setManageRequestId(Long manageRequestId) {
		this.manageRequestId = manageRequestId;
	}

	public Integer getSeatRequested() {
		return seatRequested;
	}

	public void setSeatRequested(Integer seatRequested) {
		this.seatRequested = seatRequested;
	}

	public Date getTripDuration() {
		return tripDuration;
	}

	public void setTripDuration(Date tripDuration) {
		this.tripDuration = tripDuration;
	}
	
}
