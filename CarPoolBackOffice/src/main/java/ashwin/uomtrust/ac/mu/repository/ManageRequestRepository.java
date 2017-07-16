package ashwin.uomtrust.ac.mu.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ashwin.uomtrust.ac.mu.entity.ManageRequest;
import ashwin.uomtrust.ac.mu.enums.RequestStatus;



@Repository
public interface ManageRequestRepository extends JpaRepository<ManageRequest, Long> {	
		
	@Query("select m from ManageRequest m join m.request r where r.requestId =:requestId")
	public List<ManageRequest> getManageRequestByRequestId(@Param("requestId") Long requestId);
	
	@Query("select m from ManageRequest m join m.request r where r.requestId =:requestId and m.requestStatus =:requestStatus")
	public List<ManageRequest> getManageRequestByRequestIdAndRequestStatus(@Param("requestId") Long requestId, @Param("requestStatus") RequestStatus requestStatus);	
	
	@Query("select m from ManageRequest m join m.car c where c.carId =:carId and m.requestStatus =:requestStatus")
	public List<ManageRequest> getDriverManageRequestByRequestStatus(@Param("carId") Long carId, @Param("requestStatus") RequestStatus requestStatus);
	
	@Modifying
	@Query("delete from ManageRequest m where m.manageRequestId =:manageRequestId")
	@Transactional
	public void driverDeleteClieRequest(@Param("manageRequestId") Long manageRequestId);
}
