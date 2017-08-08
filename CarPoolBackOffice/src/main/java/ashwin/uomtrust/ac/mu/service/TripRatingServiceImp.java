package ashwin.uomtrust.ac.mu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ashwin.uomtrust.ac.mu.dto.TripRatingDTO;
import ashwin.uomtrust.ac.mu.entity.Account;
import ashwin.uomtrust.ac.mu.entity.Request;
import ashwin.uomtrust.ac.mu.entity.TripRating;
import ashwin.uomtrust.ac.mu.repository.AccountRepository;
import ashwin.uomtrust.ac.mu.repository.RequestRepository;
import ashwin.uomtrust.ac.mu.repository.TripRatingRepository;

@Service
public class TripRatingServiceImp implements TripRatingService{
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TripRatingRepository tripRatingRepository;

	@Override
	public void save(TripRatingDTO tripRatingDTO) {
		// TODO Auto-generated method stub
		Account a = accountRepository.findByAccountId(tripRatingDTO.getAccountId());

		Request r = requestRepository.getRequestById(tripRatingDTO.getRequestId());
		
		TripRating tripRating = new TripRating();
		tripRating.setRequest(r);
		tripRating.setUserAccount(a);
		
		tripRatingRepository.save(tripRating);		
	}

	@Override
	public Double getDriverRating(Long accountId) {
		// TODO Auto-generated method stub
		return tripRatingRepository.getDriverRating(accountId);
	}
}
