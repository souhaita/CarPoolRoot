package ashwin.uomtrust.ac.mu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ashwin.uomtrust.ac.mu.dto.DeviceDTO;
import ashwin.uomtrust.ac.mu.entity.Account;
import ashwin.uomtrust.ac.mu.entity.Device;
import ashwin.uomtrust.ac.mu.repository.AccountRepository;
import ashwin.uomtrust.ac.mu.repository.DeviceRepository;

@Service
public class DeviceServiceImp implements DeviceService{
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;

	@Override
	public DeviceDTO save(DeviceDTO deviceDTO) {
		// TODO Auto-generated method stub
		Device device = new Device();
		
		if(deviceDTO.getDeviceId() != null){
			device.setDeviceId(deviceDTO.getDeviceId());
		}
		
		device.setDeviceToken(deviceDTO.getDeviceToken());
		
		Account account = accountRepository.findByAccountId(deviceDTO.getAccountId());
		device.setUserAccount(account);
		
		Device newDevice = deviceRepository.save(device);
		
		deviceDTO.setDeviceId(newDevice.getDeviceId());
		
		return deviceDTO;
	}

	@Override
	public void delete(DeviceDTO deviceDTO) {
		// TODO Auto-generated method stub
		deviceRepository.delete(deviceDTO.getDeviceId());
	}	
}
