package ashwin.uomtrust.ac.mu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ashwin.uomtrust.ac.mu.entity.Account;
import ashwin.uomtrust.ac.mu.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
	public Account createAdmin(@RequestBody Account account) {
		if(account != null && account.getEmail() !=null )
			return accountService.saveAccount(account);
		return null;
		
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	public Account createAccount(@RequestBody Account account) {
		if(account != null && account.getEmail() !=null ){
			return accountService.saveAccount(account);
		}
		return null;
	}

}
