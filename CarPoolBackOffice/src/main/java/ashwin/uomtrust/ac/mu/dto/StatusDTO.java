package ashwin.uomtrust.ac.mu.dto;

import java.io.Serializable;
import java.util.Date;

import ashwin.uomtrust.ac.mu.enums.AccountRole;
import ashwin.uomtrust.ac.mu.enums.AccountStatus;


/**
 * Created by vgobin on 02-Jun-17.
 */

public class StatusDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private int userStatus;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}    
}
