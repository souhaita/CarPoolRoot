package ashwin.uomtrust.ac.mu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ashwin.uomtrust.ac.mu.entity.Device;



@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {	
	
	
}
