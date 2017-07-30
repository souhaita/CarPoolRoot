package ashwin.uomtrust.ac.mu.dto;

import java.io.Serializable;


/**
 * Created by Ashwin on 05-Jun-17.
 */

public class DeviceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long deviceId;
    private Long accountId;

    private String deviceToken;

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
}
