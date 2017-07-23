package rode1lift.ashwin.uomtrust.mu.rod1lift.DTO;

import java.io.Serializable;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class DeviceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer deviceId;
    private Integer accountId;

    private String deviceToken;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
