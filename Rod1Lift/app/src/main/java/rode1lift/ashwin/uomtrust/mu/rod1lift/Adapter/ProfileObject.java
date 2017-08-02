package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.content.Intent;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.ViewType;


/**
 * Created by vgobin on 02-Jun-17.
 */

public class ProfileObject{

    private ViewType viewType;

    private byte [] carsPicture;

    private byte [] profilePicture;

    private String data;
    private String label;

    private Integer otherUserId;
    private Integer otherUserPhoneNumber;

    public ProfileObject(ViewType viewType, AccountDTO accountDTO){
        this.viewType = viewType;
        this.profilePicture = accountDTO.getProfilePicture();
        this.label = accountDTO.getFullName();
        this.otherUserId = accountDTO.getAccountId();
        this.otherUserPhoneNumber = accountDTO.getPhoneNum();
    }

    public ProfileObject(ViewType viewType, byte [] carsPictures){
        this.viewType = viewType;
        this.carsPicture = carsPictures;
    }

    public ProfileObject(ViewType viewType, String label, String data){
        this.viewType = viewType;
        this.data = data;
        this.label = label;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public byte[] getCarsPicture() {
        return carsPicture;
    }

    public void setCarsPicture(byte[] carsPicture) {
        this.carsPicture = carsPicture;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(Integer otherUserId) {
        this.otherUserId = otherUserId;
    }

    public Integer getOtherUserPhoneNumber() {
        return otherUserPhoneNumber;
    }

    public void setOtherUserPhoneNumber(Integer otherUserPhoneNumber) {
        this.otherUserPhoneNumber = otherUserPhoneNumber;
    }
}
