package ashwin.uomtrust.ac.mu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ashwin.uomtrust.ac.mu.dto.AccountDTO;
import ashwin.uomtrust.ac.mu.dto.CarDTO;
import ashwin.uomtrust.ac.mu.entity.Account;
import ashwin.uomtrust.ac.mu.service.AccountService;
import ashwin.uomtrust.ac.mu.service.CarService;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
	public Account createAdmin(@RequestBody Account account) {
		if(account != null && account.getEmail() !=null )
			return accountService.saveAccount(account);
		return null;
		
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	public AccountDTO createAccount(@RequestBody AccountDTO accountDTO) {
		if(accountDTO != null && accountDTO.getEmail() !=null ){
			return accountService.saveAccount(accountDTO);
		}
		return null;
	}
	
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/checkAccountViaEmail", method = RequestMethod.POST)
	public Account checkAccountViaEmail(@RequestBody Account account) {
		if(account != null && account.getEmail() !=null ){
			return accountService.findByEmail(account.getEmail());
		}
		return null;
	}
	
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/driverCreateCar", method = RequestMethod.POST)
	public CarDTO driverCreateCar(@RequestBody CarDTO carDTO) {
		if(carDTO != null && carDTO.getCarId() !=null ){
			return carService.saveCar(carDTO);
		}
		return null;
	}

}
