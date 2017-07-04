package ashwin.uomtrust.ac.mu.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Car implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5430841335975955046L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer carId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "accountId", nullable = true)
	private Account userAccount;
	
	private String make;
	private String model;
	private String plateNum;
	
	private Integer year;
	private Integer numOfPassenger;
	
	
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public Account getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(Account userAccount) {
		this.userAccount = userAccount;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getPlateNum() {
		return plateNum;
	}
	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getNumOfPassenger() {
		return numOfPassenger;
	}
	public void setNumOfPassenger(Integer numOfPassenger) {
		this.numOfPassenger = numOfPassenger;
	}
	
}
