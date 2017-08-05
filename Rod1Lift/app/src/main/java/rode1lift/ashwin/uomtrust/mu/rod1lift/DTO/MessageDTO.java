package rode1lift.ashwin.uomtrust.mu.rod1lift.DTO;

import java.io.Serializable;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer messageId;
    private Integer accountId;
    private Integer otherUserId;

    private String message;
    private String sProfilePicture;
    private String senderFullName;

    private boolean fromUser;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(Integer otherUserId) {
        this.otherUserId = otherUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFromUser() {
        return fromUser;
    }

    public void setFromUser(boolean fromUser) {
        this.fromUser = fromUser;
    }

    public String getsProfilePicture() {
        return sProfilePicture;
    }

    public void setsProfilePicture(String sProfilePicture) {
        this.sProfilePicture = sProfilePicture;
    }

    public String getSenderFullName() {
        return senderFullName;
    }

    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
    }
}
