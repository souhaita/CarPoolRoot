package ashwin.uomtrust.ac.mu.service;

import ashwin.uomtrust.ac.mu.dto.CarDTO;

public interface CarService {

	public CarDTO saveCar(CarDTO carDTO);

	public CarDTO findByCarId(Long carId);

}
