package ashwin.uomtrust.ac.mu.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ashwin.uomtrust.ac.mu.entity.Request;
import ashwin.uomtrust.ac.mu.enums.RequestStatus;



@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {	
	
	@Query("select r from Request r join r.account a where r.account = a and a.accountId =:accountId and r.requestStatus in (:requestStatusList) order by r.eventDate asc")
	public List<Request> getPendingRequestByUserId(@Param("accountId") Long accountId, @Param("requestStatusList") List<RequestStatus> requestStatusList);
	
	@Modifying
	@Query("delete from Request r where r.requestId =:requestId")
	@Transactional
	public void driverDeleteRequest(@Param("requestId") Long requestId);
	
	@Query("select r from Request r where r.requestId =:requestId")
	public Request getRequestById(@Param("requestId") Long requestId);		
	
	@Query("select r from Request r join r.account a where a.accountId =:accountId and r.eventDate < current_date and r.requestStatus in (:requestStatusList) order by r.eventDate desc")
	public List<Request> getRequestHistoryForDriver(@Param("accountId") Long accountId, @Param("requestStatusList") List<RequestStatus> requestStatusList);		
	
	
	@Query("select r from Request r where r.requestStatus in (:requestStatusList)")
	public List<Request> getRequestByStatus(@Param("requestStatusList") List<RequestStatus> requestStatusList);		
}
