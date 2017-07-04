package ashwin.uomtrust.ac.mu;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import ashwin.uomtrust.ac.mu.enums.AccountRole;


public class BackOfficeUser extends User {
		
	private static final long serialVersionUID = -1460047265506653280L;

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	 public BackOfficeUser(Long id, String email, boolean enabled, Collection<? extends GrantedAuthority> authorities ) {
	    	super(email,null , enabled, true, true, true, authorities);
			this.id = id;
	 }
}