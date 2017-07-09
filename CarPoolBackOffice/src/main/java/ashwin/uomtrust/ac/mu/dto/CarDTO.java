package ashwin.uomtrust.ac.mu.dto;

import java.io.Serializable;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class CarDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long carId;
    private Long accountId;
    
    private Integer year;
    private Integer numOfPassenger;

    private String make;
    private String plateNum;
    private String model;
    private String sPicture1;
    private String sPicture2;
    private String sPicture3;
    private String sPicture4;

    private byte[] picture1;
    private byte[] picture2;
    private byte[] picture3;
    private byte[] picture4;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public byte[] getPicture1() {
        return picture1;
    }

    public void setPicture1(byte[] picture1) {
        this.picture1 = picture1;
    }

    public byte[] getPicture2() {
        return picture2;
    }

    public void setPicture2(byte[] picture2) {
        this.picture2 = picture2;
    }

    public byte[] getPicture3() {
        return picture3;
    }

    public void setPicture3(byte[] picture3) {
        this.picture3 = picture3;
    }

    public byte[] getPicture4() {
        return picture4;
    }

    public void setPicture4(byte[] picture4) {
        this.picture4 = picture4;
    }

	public String getsPicture1() {
		return sPicture1;
	}

	public void setsPicture1(String sPicture1) {
		this.sPicture1 = sPicture1;
	}

	public String getsPicture2() {
		return sPicture2;
	}

	public void setsPicture2(String sPicture2) {
		this.sPicture2 = sPicture2;
	}

	public String getsPicture3() {
		return sPicture3;
	}

	public void setsPicture3(String sPicture3) {
		this.sPicture3 = sPicture3;
	}

	public String getsPicture4() {
		return sPicture4;
	}

	public void setsPicture4(String sPicture4) {
		this.sPicture4 = sPicture4;
	}
}
