package shashi.uomtrust.ac.mu.service;



import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shashi.uomtrust.ac.mu.dto.CarDetailsDTO;
import shashi.uomtrust.ac.mu.entity.Account;
import shashi.uomtrust.ac.mu.entity.CarDetails;
import shashi.uomtrust.ac.mu.enums.UserRole;
import shashi.uomtrust.ac.mu.repository.AccountRepository;
import shashi.uomtrust.ac.mu.repository.CarDetailsRepository;
import shashi.uomtrust.ac.mu.utils.Utils;

@Service
public class CarDetailsImp implements CarDetailsService{
	
	@Autowired
	private CarDetailsRepository carDetailsRepository;
	
	@Autowired
	private AccountRepository accountRepository ;
	

	@Override
	public CarDetailsDTO saveCardetails(CarDetailsDTO carDetailsDTO) {
		
		Account account = accountRepository.findByAccountId(carDetailsDTO.getAccountId());
		account.setRole(UserRole.TAXI_DRIVER);
		accountRepository.save(account);
		
		
		CarDetails carDetails = new CarDetails();
		carDetails.setAccount(account);
		carDetails.setMake(carDetailsDTO.getMake());
		carDetails.setNumOfPassenger(carDetailsDTO.getNumOfPassenger());
		carDetails.setYear(carDetailsDTO.getYear());
		carDetails.setPlateNum(carDetailsDTO.getPlateNum());
		
		CarDetails newCardetails = carDetailsRepository.save(carDetails);
		
		CarDetailsDTO newCarDetailsDTO = new CarDetailsDTO();
		newCarDetailsDTO.setCarId(newCardetails.getCarId());
		newCarDetailsDTO.setYear(newCardetails.getYear());
		newCarDetailsDTO.setAccountId(newCardetails.getAccount().getAccountId());
		newCarDetailsDTO.setMake(newCardetails.getMake());
		newCarDetailsDTO.setNumOfPassenger(newCardetails.getNumOfPassenger());		

		return newCarDetailsDTO;
	}


	@Override
	public CarDetailsDTO findByCarId(Integer carId) {
		// TODO Auto-generated method stub
		CarDetails newCardetails = carDetailsRepository.getCarById(carId);
		
		CarDetailsDTO newCarDetailsDTO = new CarDetailsDTO();
		newCarDetailsDTO.setCarId(newCardetails.getCarId());
		newCarDetailsDTO.setYear(newCardetails.getYear());
		newCarDetailsDTO.setAccountId(newCardetails.getAccount().getAccountId());
		newCarDetailsDTO.setMake(newCardetails.getMake());
		newCarDetailsDTO.setNumOfPassenger(newCardetails.getNumOfPassenger());	
		newCarDetailsDTO.setPlateNum(newCardetails.getPlateNum());	

		Utils.getImage(newCarDetailsDTO);
		
		return newCarDetailsDTO;
	}

	
}
