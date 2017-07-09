package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

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

    public ProfileObject(ViewType viewType, AccountDTO accountDTO){
        this.viewType = viewType;
        this.profilePicture = accountDTO.getProfilePicture();
        this.label = accountDTO.getFirstName();
        this.data = accountDTO.getLastName();
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
}
