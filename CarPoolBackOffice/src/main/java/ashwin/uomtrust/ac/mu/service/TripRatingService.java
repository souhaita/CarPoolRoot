package ashwin.uomtrust.ac.mu.service;

import ashwin.uomtrust.ac.mu.dto.TripRatingDTO;

public interface TripRatingService {

	public void save(TripRatingDTO tripRatingDTO);
	public Double getDriverRating(Long accountId);
}
