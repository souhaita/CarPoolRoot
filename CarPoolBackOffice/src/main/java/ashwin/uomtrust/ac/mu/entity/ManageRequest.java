package ashwin.uomtrust.ac.mu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ashwin.uomtrust.ac.mu.enums.RequestStatus;

@Entity
public class ManageRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -232505228828580813L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long manageRequestId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "requestId", nullable = true)
	private Request request;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "accountId", nullable = true)
	private Account userAccount;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "carId", nullable = true)
	private Car car;
	
	private Date dateCreated;
	private Date dateUpdated;
		
	private RequestStatus requestStatus;

	public Long getManageRequestId() {
		return manageRequestId;
	}

	public void setManageRequestId(Long manageRequestId) {
		this.manageRequestId = manageRequestId;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Account getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(Account userAccount) {
		this.userAccount = userAccount;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
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
