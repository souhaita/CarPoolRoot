package ashwin.uomtrust.ac.mu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ashwin.uomtrust.ac.mu.entity.Car;



@Repository
public interface CarRepository extends JpaRepository<Car, Long> {	
	
	@Query("select c from Car c where c.carId =:carId")
	public Car getCarById(@Param("carId") Long carId);
	
	@Query("select c from Car c join c.userAccount a where a.accountId =:accountId")
	public Car getCarByAccountId(@Param("accountId") Long accountId);

		
}
