package ashwin.uomtrust.ac.mu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ashwin.uomtrust.ac.mu.dto.ManageRequestDTO;
import ashwin.uomtrust.ac.mu.dto.RatingDTO;
import ashwin.uomtrust.ac.mu.dto.RequestDTO;
import ashwin.uomtrust.ac.mu.dto.RequestObject;
import ashwin.uomtrust.ac.mu.entity.Account;
import ashwin.uomtrust.ac.mu.repository.RatingRepository;
import ashwin.uomtrust.ac.mu.service.AccountService;
import ashwin.uomtrust.ac.mu.service.CarService;
import ashwin.uomtrust.ac.mu.service.ManageRequestService;
import ashwin.uomtrust.ac.mu.service.RequestService;
import ashwin.uomtrust.ac.mu.service.RatingService;

@RestController
@RequestMapping("/api/request")
public class RequestController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ManageRequestService manageRequestService;
	
	@Autowired
	private RatingService ratingService;
	
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(value = "/driverCreateUpdateRequest", method = RequestMethod.POST)
	public RequestDTO driverCreateUpdateRequest(@RequestBody RequestDTO requestDTO) {    	
		return requestService.save(requestDTO);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/driverGetPendingRequestList", method = RequestMethod.POST)
   	public List<RequestObject> driverGetPendingRequestList(@RequestBody RequestDTO requestDTO) {    	    	
    	return requestService.driverGetPendingRequestList(requestDTO);
   	}

	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/driverDeletePendingRequest", method = RequestMethod.POST)
   	public Boolean driverDeletePendingRequest(@RequestBody RequestDTO requestDTO) {    	    	
    	return requestService.driverDeletePendingRequest(requestDTO.getRequestId());
   	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/driverDeleteClientRequest", method = RequestMethod.POST)
   	public Boolean driverDeleteClientRequest(@RequestBody RequestDTO requestDTO) {    	
    	return manageRequestService.driverDeleteClientRequest(requestDTO.getManageRequestId());
   	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/driverAcceptClientRequest", method = RequestMethod.POST)
   	public Boolean driverAcceptClientRequest(@RequestBody RequestDTO requestDTO) {    	
    	return manageRequestService.driverAcceptClientRequest(requestDTO.getManageRequestId());
   	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/driverGetUserAcceptedRequestList", method = RequestMethod.POST)
   	public List<RequestObject> driverGetUserAcceptedRequestList(@RequestBody RequestDTO requestDTO) {    	    	
    	return manageRequestService.driverGetUserAcceptedRequestList(requestDTO);
   	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/driverGetHistoryList", method = RequestMethod.POST)
   	public List<RequestObject>  driverGetHistoryList(@RequestBody Account account) {    	    	
    	return requestService.driverGetHistoryList(account);
   	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/passengerGetNewList", method = RequestMethod.POST)
   	public List<RequestObject> passengerGetNewList(@RequestBody RequestDTO requestDTO) {    	    	
    	return requestService.passengerGetNewList(requestDTO);
   	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/passengerDeleteRequest", method = RequestMethod.POST)
   	public Boolean passengerDeleteRequest(@RequestBody RequestDTO requestDTO) {    	
    	return manageRequestService.passengerDeleteRequest(requestDTO);
   	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/passengerAcceptRequest", method = RequestMethod.POST)
   	public boolean passengerAcceptRequest(@RequestBody ManageRequestDTO manageRequestDTO) {    	
    	return manageRequestService.passengerAcceptRequest(manageRequestDTO);
   	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/passengerGetPendingList", method = RequestMethod.POST)
   	public List<RequestObject> passengerGetPendingList(@RequestBody RequestDTO requestDTO) {    	    	
    	return manageRequestService.passengerGetPendingList(requestDTO);
   	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/passengerGetAcceptedRequest", method = RequestMethod.POST)
   	public List<RequestObject> passengerGetAcceptedRequest(@RequestBody RequestDTO requestDTO) {    	    	
    	return manageRequestService.passengerGetAcceptedRequest(requestDTO);
   	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/passengerGetHistoryList", method = RequestMethod.POST)
   	public List<RequestObject> passengerGetHistoryList(@RequestBody RequestDTO requestDTO) {    	    	
    	return manageRequestService.passengerGetHistoryList(requestDTO);
   	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/passengerPayRequest", method = RequestMethod.POST)
   	public boolean passengerPayRequest(@RequestBody ManageRequestDTO manageRequestDTO) {    	    	
    	return manageRequestService.passengerPayRequest(manageRequestDTO);
   	}
	
	@CrossOrigin(origins = "http://localhost:8081")
   	@RequestMapping(value = "/passengerRateTrip", method = RequestMethod.POST)
   	public void passengerRateTrip(@RequestBody RatingDTO ratingDTO) {    	    	
		ratingService.save(ratingDTO);
   	}
	
}
