package ashwin.uomtrust.ac.mu.service;

import ashwin.uomtrust.ac.mu.dto.RatingDTO;

public interface RatingService {

	public void save(RatingDTO ratingDTO);
	public Double getDriverRating(Long accountId);
}
