package rode1lift.ashwin.uomtrust.mu.rod1lift.DTO;

import java.io.Serializable;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class CarDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer carId;
    private Integer accountId;
    private Integer year;
    private Integer numOfPassenger;

    private String make;
    private String plateNum;
    private String model;

    private byte[] picture1;
    private byte[] picture2;
    private byte[] picture3;
    private byte[] picture4;

    private boolean hasPic1;
    private boolean hasPic2;
    private boolean hasPic3;
    private boolean hasPic4;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
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

    public boolean isHasPic1() {
        return hasPic1;
    }

    public void setHasPic1(boolean hasPic1) {
        this.hasPic1 = hasPic1;
    }

    public boolean isHasPic2() {
        return hasPic2;
    }

    public void setHasPic2(boolean hasPic2) {
        this.hasPic2 = hasPic2;
    }

    public boolean isHasPic3() {
        return hasPic3;
    }

    public void setHasPic3(boolean hasPic3) {
        this.hasPic3 = hasPic3;
    }

    public boolean isHasPic4() {
        return hasPic4;
    }

    public void setHasPic4(boolean hasPic4) {
        this.hasPic4 = hasPic4;
    }
}
