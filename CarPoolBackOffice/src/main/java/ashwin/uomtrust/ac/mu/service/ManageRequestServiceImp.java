package ashwin.uomtrust.ac.mu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ashwin.uomtrust.ac.mu.entity.ManageRequest;
import ashwin.uomtrust.ac.mu.repository.AccountRepository;
import ashwin.uomtrust.ac.mu.repository.ManageRequestRepository;
import ashwin.uomtrust.ac.mu.repository.RequestRepository;

@Service
public class ManageRequestServiceImp implements ManageRequestService{
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private ManageRequestRepository manageRequestRepository;

	@Override
	public List<ManageRequest> getManageRequestByRequestId(Long requestId) {
		// TODO Auto-generated method stub
		return manageRequestRepository.getManageRequestByRequestId(requestId);
	}

	
	

}