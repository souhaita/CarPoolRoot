package rode1lift.ashwin.uomtrust.mu.rod1lift.DTO;

import java.io.Serializable;
import java.util.Date;

import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountRole;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountStatus;


/**
 * Created by vgobin on 02-Jun-17.
 */

public class AccountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer accountId;
    private Integer phoneNum;
    private Integer ratingCount;

    private String email;
    private String fullName;
    private String facebookId;
    private String sProfilePicture;
    private String googleId;
    private String googlePhotoPath;

    private AccountRole accountRole;
    private AccountStatus accountStatus;

    private Date dateCreated;
    private Date dateUpdated;

    private byte [] profilePicture;

    private Double rating;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public AccountRole getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
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

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getsProfilePicture() {
        return sProfilePicture;
    }

    public void setsProfilePicture(String sProfilePicture) {
        this.sProfilePicture = sProfilePicture;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getGooglePhotoPath() {
        return googlePhotoPath;
    }

    public void setGooglePhotoPath(String googlePhotoPath) {
        this.googlePhotoPath = googlePhotoPath;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }
}
