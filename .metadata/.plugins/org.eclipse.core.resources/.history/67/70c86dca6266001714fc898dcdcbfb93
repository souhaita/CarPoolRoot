package ashwin.uomtrust.ac.mu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ashwin.uomtrust.ac.mu.dto.RequestDTO;
import ashwin.uomtrust.ac.mu.service.AccountService;
import ashwin.uomtrust.ac.mu.service.CarService;
import ashwin.uomtrust.ac.mu.service.RequestService;

@RestController
@RequestMapping("/api/request")
public class RequestController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private RequestService requestService;
	
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/driverCreateUpdateRequest", method = RequestMethod.POST)
	public RequestDTO driverCreateUpdateRequest(@RequestBody RequestDTO requestDTO) {    	
		return requestService.save(requestDTO);
	}

}
