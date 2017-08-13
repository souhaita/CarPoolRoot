package ashwin.uomtrust.ac.mu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ashwin.uomtrust.ac.mu.entity.Rating;



@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {	
	
	@Query("select avg(r.rating) from Rating r join r.account a where a.accountId =:accountId")
	public Double getRating(@Param("accountId") Long accountId);
	
	@Query("select count(r.rating) from Rating r join r.account a where a.accountId =:accountId")
	public Integer getRatingCount(@Param("accountId") Long accountId);
	
}
