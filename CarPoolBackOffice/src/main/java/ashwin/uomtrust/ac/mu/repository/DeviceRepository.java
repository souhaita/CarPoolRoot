package ashwin.uomtrust.ac.mu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ashwin.uomtrust.ac.mu.entity.Device;



@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {	
	
	@Query("select d from Device d join d.userAccount a where a.accountId =:accountId")
	public List<Device> getDeviceByAccountId(@Param("accountId") Long accountId);
}
