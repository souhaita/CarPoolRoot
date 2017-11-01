package ashwin.uomtrust.ac.mu.service;

import ashwin.uomtrust.ac.mu.dto.AccountDTO;
import ashwin.uomtrust.ac.mu.entity.Account;

public interface AccountService {

	public Account findByAccountId(Long accountId);
	public AccountDTO findByEmail(String email);
	public AccountDTO saveAccount(AccountDTO accountDTO);
	public Account saveAccount(Account account);
	
	//admin
	public int getTotalCarSeekerCreatedToday();
	public int getTotalCarPoolerCreatedToday();
	public int getTotalUser();

}
