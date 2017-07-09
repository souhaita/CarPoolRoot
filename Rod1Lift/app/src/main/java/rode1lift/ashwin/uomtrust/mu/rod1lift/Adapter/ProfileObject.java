package rode1lift.ashwin.uomtrust.mu.rod1lift.DTO;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.Const;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.CarDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountRole;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.ViewType;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by vgobin on 02-Jun-17.
 */

public class ProfileObject{

    private ViewType viewType;

    private List<byte []> carsPictures;

    private byte [] profilePicture;

    private String data;

    public ProfileObject(ViewType viewType, byte [] profilePicture){
        this.viewType = viewType;
        this.profilePicture = profilePicture;
    }

    public ProfileObject(ViewType viewType, List<byte []> carsPictures){
        this.viewType = viewType;
        this.carsPictures = carsPictures;
    }

    public ProfileObject(ViewType viewType, String data){
        this.viewType = viewType;
        this.data = data;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public List<byte[]> getCarsPictures() {
        return carsPictures;
    }

    public void setCarsPictures(List<byte[]> carsPictures) {
        this.carsPictures = carsPictures;
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
}
