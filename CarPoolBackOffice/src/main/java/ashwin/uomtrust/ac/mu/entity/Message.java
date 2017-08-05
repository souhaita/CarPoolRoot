package ashwin.uomtrust.ac.mu.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5430841335975955046L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long messageId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "accountId", nullable = true)
	private Account account;
	
	private String message;
	
	private Long otherUserId;
	
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getOtherUserId() {
		return otherUserId;
	}
	public void setOtherUserId(Long otherUserId) {
		this.otherUserId = otherUserId;
	}
}
