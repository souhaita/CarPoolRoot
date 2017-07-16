package ashwin.uomtrust.ac.mu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ashwin.uomtrust.ac.mu.entity.ManageRequest;



@Repository
public interface ManageRequestRepository extends JpaRepository<ManageRequest, Long> {	
		
	@Query("select m from ManageRequest m join m.request r where r.requestId =:requestId")
	public List<ManageRequest> getManageRequestByRequestId(@Param("requestId") Long requestId);

}