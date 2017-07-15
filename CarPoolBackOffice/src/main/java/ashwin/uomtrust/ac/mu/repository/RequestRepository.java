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
	
	/*@Modifying
	@Query("update Request r set r.requestStatus = :requestStatus where r.requestId =:requestId order by r.requestId desc")
	@Transactional
	public void updateRequestStatustById(@Param("requestId") Integer requestId, @Param("requestStatus") RequestStatus requestStatus );
	
	@Query("select r from Request r where r.requestStatus =:requestStatus order by r.requestId desc")
	public List<Request> getListRequestsByStatus(@Param("requestStatus") RequestStatus requestStatus);
	
	
	@Query("select r from Request r join r.account a where r.requestStatus = :request_status order by r.requestId desc")
	public List<Request> getRequestByStatusForTaxi(@Param("requestStatus") Integer requestStatus);*/
	
	@Query("select r from Request r join r.account a where r.account = a and a.accountId =:accountId and r.requestStatus =:requestStatus order by r.requestId desc")
	public List<Request> getRequestByUserIdAndRequestStatus(@Param("accountId") Long accountId,@Param("requestStatus") RequestStatus requestStatus);
	
	@Modifying
	@Query("delete from Request r where r.requestId =:requestId")
	@Transactional
	public void driverDeleteRequest(@Param("requestId") Long requestId);
	
	@Query("select r from Request r where r.requestId =:requestId")
	public Request getRequestById(@Param("requestId") Long requestId);		
}