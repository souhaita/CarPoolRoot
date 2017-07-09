package ashwin.uomtrust.ac.mu.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ashwin.uomtrust.ac.mu.dto.CarDTO;
import ashwin.uomtrust.ac.mu.entity.Account;
import ashwin.uomtrust.ac.mu.entity.Car;
import ashwin.uomtrust.ac.mu.enums.AccountRole;
import ashwin.uomtrust.ac.mu.repository.AccountRepository;
import ashwin.uomtrust.ac.mu.repository.CarRepository;
import ashwin.uomtrust.ac.mu.utils.Utils;



@Service
public class CarServiceImp implements CarService{
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private AccountRepository accountRepository ;
	

	@Override
	public CarDTO saveCar(CarDTO carDTO) {
		
		Account account = accountRepository.findByAccountId(carDTO.getAccountId());
		account.setAccountRole(AccountRole.DRIVER);
		accountRepository.save(account);
		
		Car car = new Car();
		car.setUserAccount(account);
		car.setMake(carDTO.getMake());
		car.setNumOfPassenger(carDTO.getNumOfPassenger());
		car.setYear(carDTO.getYear());
		car.setPlateNum(carDTO.getPlateNum());
		car.setModel(carDTO.getModel());
		
		Car newCar = carRepository.save(car);
		
		CarDTO newCarDTO = new CarDTO();
		newCarDTO.setCarId(newCar.getCarId());
		newCarDTO.setYear(newCar.getYear());
		newCarDTO.setAccountId(newCar.getUserAccount().getAccountId());
		newCarDTO.setMake(newCar.getMake());
		newCarDTO.setNumOfPassenger(newCar.getNumOfPassenger());		
		newCarDTO.setModel(newCar.getModel());		

		Utils.saveImageToServer(carDTO);

		return newCarDTO;
	}


	@Override
	public CarDTO findByCarId(Long carId) {
		// TODO Auto-generated method stub
		Car newCar = carRepository.getCarById(carId);
		
		CarDTO newCarDTO = new CarDTO();
		newCarDTO.setCarId(newCar.getCarId());
		newCarDTO.setYear(newCar.getYear());
		newCarDTO.setAccountId(newCar.getUserAccount().getAccountId());
		newCarDTO.setMake(newCar.getMake());
		newCarDTO.setNumOfPassenger(newCar.getNumOfPassenger());	
		newCarDTO.setPlateNum(newCar.getPlateNum());	
		newCarDTO.setModel(newCar.getModel());		

		Utils.getImage(newCarDTO);
		
		return newCarDTO;
	}

	
}
