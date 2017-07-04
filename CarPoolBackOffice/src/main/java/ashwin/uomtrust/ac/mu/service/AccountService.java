package ashwin.uomtrust.ac.mu.service;

import ashwin.uomtrust.ac.mu.entity.Account;

public interface AccountService {

	public Account findByAccountId(Long accountId);
	public Account findByEmail(String email);
	public Account saveAccount(Account account);
}
