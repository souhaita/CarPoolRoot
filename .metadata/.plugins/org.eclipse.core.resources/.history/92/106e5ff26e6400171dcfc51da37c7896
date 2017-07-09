package shashi.uomtrust.ac.mu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shashi.uomtrust.ac.mu.dto.CarDetailsDTO;
import shashi.uomtrust.ac.mu.entity.CarDetails;
import shashi.uomtrust.ac.mu.entity.Request;



@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {	
	
	@Query("select c from CarDetails c where c.carId =:car_id")
	public CarDetails getCarById(@Param("car_id") Integer car_id);
	
	@Query("select c from CarDetails c join c.account a where a.accountId =:accountId")
	public CarDetails getCarByAccountId(@Param("accountId") Integer accountId);
		
}
