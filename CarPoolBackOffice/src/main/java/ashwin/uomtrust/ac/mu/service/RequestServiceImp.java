package ashwin.uomtrust.ac.mu.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ashwin.uomtrust.ac.mu.dto.AccountDTO;
import ashwin.uomtrust.ac.mu.dto.ManageRequestDTO;
import ashwin.uomtrust.ac.mu.dto.RequestDTO;
import ashwin.uomtrust.ac.mu.dto.RequestObject;
import ashwin.uomtrust.ac.mu.entity.Account;
import ashwin.uomtrust.ac.mu.entity.Car;
import ashwin.uomtrust.ac.mu.entity.ManageRequest;
import ashwin.uomtrust.ac.mu.entity.Request;
import ashwin.uomtrust.ac.mu.enums.RequestStatus;
import ashwin.uomtrust.ac.mu.repository.AccountRepository;
import ashwin.uomtrust.ac.mu.repository.CarRepository;
import ashwin.uomtrust.ac.mu.repository.ManageRequestRepository;
import ashwin.uomtrust.ac.mu.repository.RequestRepository;
import ashwin.uomtrust.ac.mu.utils.Utils;

@Service
public class RequestServiceImp implements RequestService{
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private ManageRequestRepository manageRequestRepository;

	@Autowired
	private CarRepository carRepository;
	
	@Override
	public RequestDTO save(RequestDTO requestDTO) {
		Account account = accountRepository.findOne(requestDTO.getAccountId());
		
		Calendar calendar = Calendar.getInstance();
		
		Request request = new Request();
		
		if(requestDTO.getRequestId() != null && requestDTO.getRequestId()>0)
			request.setRequestId(requestDTO.getRequestId());
		
		request.setAccount(account);
		
		if(requestDTO.getDateCreated() != null){
			calendar.setTimeInMillis(requestDTO.getDateCreated().getTime());
			request.setDateCreated(calendar.getTime());
		}
		
		if(requestDTO.getDateUpdated() != null){
			calendar.setTimeInMillis(requestDTO.getDateUpdated().getTime());
			request.setDateUpdated(calendar.getTime());	
		}
		
		calendar.setTimeInMillis(requestDTO.getEventDate().getTime());
		request.setEventDate(calendar.getTime());
		
		request.setPlaceFrom(requestDTO.getPlaceFrom());
		request.setPlaceTo(requestDTO.getPlaceTo());
		request.setRequestStatus(requestDTO.getRequestStatus());
		
		request.setPrice(requestDTO.getPrice());
		request.setSeatAvailable(requestDTO.getSeatAvailable());

		
		Request newRequest = requestRepository.save(request);
		
		RequestDTO newRequestDTO = requestDTO;
		newRequestDTO.setRequestId(newRequest.getRequestId());
		
		return newRequestDTO;
	}

	@Override
	public Boolean driverDeleteRequest(Long requestId) {
		// TODO Auto-generated method stub
		requestRepository.driverDeleteRequest(requestId);
		Request request = requestRepository.getRequestById(requestId);
		Boolean result = false;
		
		if(request == null || request.getRequestId() == null)
			result = true;
		
		return result;
	}

	/*@Override
	public List<RequestDTO> getRequestByUserIdAndRequestStatus(RequestDTO requestDTO) {
		// TODO Auto-generated method stub
		List<Request> requestList = requestRepository.getRequestByUserIdAndRequestStatus(requestDTO.getAccountId(), requestDTO.getRequestStatus().getValue());
		
		List<RequestDTO> requestDTOs = new ArrayList<>();
		
		for(Request request : requestList){
			RequestDTO newRequestDTO = new RequestDTO();
			newRequestDTO.setAccountId(request.getAccount().getAccountId());
			newRequestDTO.setEventDateTime(request.getEvent_date_time().getTime());
			newRequestDTO.setPlaceFrom(request.getPlace_from());
			newRequestDTO.setPlaceTo(request.getPlace_to());
			newRequestDTO.setRequestId(request.getRequest_id());
			newRequestDTO.setDetails(request.getDetails());
			newRequestDTO.setRequestStatus(RequestStatus.valueFor(request.getRequest_status()));
			
			requestDTOs.add(newRequestDTO);
		}
		
		return requestDTOs;
	}*/
	
	/*@Override
	public List<RequestDTO> getOtherRequestByUserIdAndRequestStatus(RequestDTO requestDTO) {
		// TODO Auto-generated method stub
		
		return manageRequestService.getManageRequestByStatusForUser(requestDTO.getRequestStatus().getValue(), requestDTO.getAccountId());
	}
	*/
	
	@Override
	public List<RequestObject> driverGetPendingRequestList(RequestDTO requestDTO) {
		List<Request> requestList = requestRepository.getRequestByUserIdAndRequestStatus(requestDTO.getAccountId(), requestDTO.getRequestStatus());
				
		List<RequestObject> requestObjectList = new ArrayList<>();
		
		for(Request request : requestList){
			RequestObject requestObject = new RequestObject();
			
			RequestDTO newRequestDTO = new RequestDTO();
			newRequestDTO.setAccountId(request.getAccount().getAccountId());
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(request.getEventDate().getTime());
			newRequestDTO.setEventDate(calendar.getTime());
			newRequestDTO.setPlaceFrom(request.getPlaceFrom());
			newRequestDTO.setPlaceTo(request.getPlaceTo());
			newRequestDTO.setRequestId(request.getRequestId());
			newRequestDTO.setRequestStatus(request.getRequestStatus());
			newRequestDTO.setPrice(request.getPrice());
			newRequestDTO.setSeatAvailable(request.getSeatAvailable());
			
			Car car = carRepository.getCarByAccountId(request.getAccount().getAccountId());
			newRequestDTO.setCarId(car.getCarId());

			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(request.getDateCreated().getTime());
			newRequestDTO.setDateCreated(cal.getTime());
			
			cal.setTimeInMillis(request.getDateUpdated().getTime());
			newRequestDTO.setDateUpdated(cal.getTime());
			
			cal.setTimeInMillis(request.getEventDate().getTime());
			newRequestDTO.setEventDate(cal.getTime());
			
			newRequestDTO.setAccountId(request.getAccount().getAccountId());			
			
			List<ManageRequest> manageRequestList = manageRequestRepository.getManageRequestByRequestId(request.getRequestId());
			List<ManageRequestDTO> manageRquestDTOList = new ArrayList<>();
			List<AccountDTO> accountDTOList =  new ArrayList<>();
			
			for(ManageRequest m : manageRequestList){
				ManageRequestDTO manageRequestDTO = new ManageRequestDTO();
				manageRequestDTO.setAccountId(m.getUserAccount().getAccountId());
				manageRequestDTO.setCarId(m.getCar().getCarId());
				
				Calendar mCalendar = Calendar.getInstance();
				
				mCalendar.setTimeInMillis(m.getDateCreated().getTime());			
				manageRequestDTO.setDateCreated(mCalendar.getTime());
				
				mCalendar.setTimeInMillis(m.getDateUpdated().getTime());
				manageRequestDTO.setDateUpdated(mCalendar.getTime());
				
				manageRequestDTO.setManageRequestId(m.getManageRequestId());
				manageRequestDTO.setRequestId(m.getRequest().getRequestId());
				manageRequestDTO.setRequestStatus(m.getRequestStatus());
				
				manageRquestDTOList.add(manageRequestDTO);
				
				Account a = m.getUserAccount();
				AccountDTO accountDTO = new AccountDTO();
				accountDTO.setAccountId(a.getAccountId());				
				accountDTO.setFirstName(a.getFirstName());
				accountDTO.setLastName(a.getLastName());
				Utils.getImageProfile(accountDTO);
				
				accountDTOList.add(accountDTO);
			}
				
			requestObject.setAccountDTOList(accountDTOList);
			requestObject.setRequestDTO(newRequestDTO);
			requestObject.setManageRequestDTOList(manageRquestDTOList);			
			requestObjectList.add(requestObject);
		}
		
		return requestObjectList;
	}

	
	
	/*@Override
	public RequestDTO acceptOrRejectRequestTaxi(RequestDTO requestDTO) {
		// TODO Auto-generated method stub
		Request request = requestRepository.getRequestById(requestDTO.getRequestId());
		CarDetails carDetails = carDetailsRepository.getCarById(requestDTO.getCarId());
		
		ManageRequest manageRequest = manageRequestRepository.getManageRequestForTaxiByRequestId(requestDTO.getRequestId(), carDetails);
		
		if(manageRequest == null || manageRequest.getManage_request_id() == null)
			manageRequest= new ManageRequest();
		
		manageRequest.setCarDetails(carDetails);
		manageRequest.setRequest(request);
		
		if(requestDTO.getPrice() != null)
			manageRequest.setPrice(requestDTO.getPrice());
		
		manageRequest.setUserAccount(request.getAccount());
		
		manageRequest.setDate_created(new Date());
		manageRequest.setDate_updated(new Date());
		manageRequest.setPrice(requestDTO.getPrice());
		manageRequest.setRequest_status(requestDTO.getRequestStatus().getValue());
		
		manageRequestRepository.save(manageRequest);
		
		return requestDTO;
	}
*/
	/*@Override
	public RequestDTO acceptOrRejectRequestUser(RequestDTO requestDTO) {
		// TODO Auto-generated method stub
		Request request = requestRepository.getRequestById(requestDTO.getRequestId());
		CarDetails carDetails = carDetailsRepository.getCarById(requestDTO.getCarId());
		
		ManageRequest manageRequest = manageRequestRepository.getManageRequestForTaxiByRequestId(requestDTO.getRequestId(), carDetails);
		
		if(manageRequest == null || manageRequest.getManage_request_id() == null)
			manageRequest= new ManageRequest();
		
		manageRequest.setCarDetails(carDetails);
		manageRequest.setRequest(request);
		
		if(requestDTO.getPrice() != null)
			manageRequest.setPrice(requestDTO.getPrice());
		
		manageRequest.setUserAccount(request.getAccount());
		
		manageRequest.setDate_created(new Date());
		manageRequest.setDate_updated(new Date());
		manageRequest.setPrice(requestDTO.getPrice());
		manageRequest.setRequest_status(requestDTO.getRequestStatus().getValue());
		
		manageRequestRepository.save(manageRequest);
		
		if(requestDTO.getRequestStatus() == RequestStatus.CLIENT_ACCEPTED){
			request.setRequest_status(RequestStatus.CLIENT_ACCEPTED.getValue());
			requestRepository.save(request);
		}
		
		return requestDTO;
	}*/

}
