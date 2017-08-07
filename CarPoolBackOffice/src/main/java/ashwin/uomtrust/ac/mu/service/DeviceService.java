package ashwin.uomtrust.ac.mu.service;

import ashwin.uomtrust.ac.mu.dto.DeviceDTO;

public interface DeviceService {

	public DeviceDTO save(DeviceDTO deviceDTO);
	public void delete(DeviceDTO deviceDTO);

}
