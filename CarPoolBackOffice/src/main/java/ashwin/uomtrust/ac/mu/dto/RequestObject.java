package ashwin.uomtrust.ac.mu.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ashwin.uomtrust.ac.mu.enums.RequestStatus;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class RequestObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private RequestDTO requestDTO;
	private List<ManageRequestDTO> manageRequestDTOList;
	private List<AccountDTO> accountDTOList;
	private List<CarDTO> carDTOList;
   
	public RequestDTO getRequestDTO() {
		return requestDTO;
	}
	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}
	public List<ManageRequestDTO> getManageRequestDTOList() {
		return manageRequestDTOList;
	}
	public void setManageRequestDTOList(List<ManageRequestDTO> manageRequestDTOList) {
		this.manageRequestDTOList = manageRequestDTOList;
	}
	public List<AccountDTO> getAccountDTOList() {
		return accountDTOList;
	}
	public void setAccountDTOList(List<AccountDTO> accountDTOList) {
		this.accountDTOList = accountDTOList;
	}
	public List<CarDTO> getCarDTOList() {
		return carDTOList;
	}
	public void setCarDTOList(List<CarDTO> carDTOList) {
		this.carDTOList = carDTOList;
	}
}
