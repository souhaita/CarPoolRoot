package ashwin.uomtrust.ac.mu.service;

import java.util.List;

import ashwin.uomtrust.ac.mu.dto.RequestDTO;

public interface RequestService {

	public RequestDTO save(RequestDTO requestDTO);
	//public Boolean delete(Integer requestId);
	//public List<RequestDTO> getRequestByUserIdAndRequestStatus(RequestDTO requestDTO);
	//public List<RequestDTO> getOtherRequestByUserIdAndRequestStatus(RequestDTO requestDTO);
	public List<RequestDTO> driverGetPendingRequestList(RequestDTO requestDTO);
	//public RequestDTO acceptOrRejectRequestTaxi(RequestDTO requestDTO);
	//public RequestDTO acceptOrRejectRequestUser(RequestDTO requestDTO);

}
