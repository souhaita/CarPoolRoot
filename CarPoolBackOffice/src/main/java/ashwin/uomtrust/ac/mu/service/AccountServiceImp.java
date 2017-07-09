package ashwin.uomtrust.ac.mu.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ashwin.uomtrust.ac.mu.dto.AccountDTO;
import ashwin.uomtrust.ac.mu.entity.Account;
import ashwin.uomtrust.ac.mu.repository.AccountRepository;
import ashwin.uomtrust.ac.mu.utils.Utils;

@Service
public class AccountServiceImp implements AccountService{
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account findByAccountId(Long accountId) {
		// TODO Auto-generated method stub
		return accountRepository.findByAccountId(accountId);
	}

	@Override
	public Account findByEmail(String email) {
		// TODO Auto-generated method stub
		return accountRepository.findByEmail(email);
	}

	@Override
	public Account saveAccount(Account account) {
		// TODO Auto-generated method stub
		return accountRepository.save(account);
	}

	@Override
	public AccountDTO saveAccount(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		Account account = accountRepository.findByAccountId(accountDTO.getAccountId());
		
		if(account == null)
			account = new Account();
		else
			account.setAccountId(accountDTO.getAccountId());
		
		account.setAccountRole(accountDTO.getAccountRole());
		account.setAccountStatus(accountDTO.getAccountStatus());
		
		Calendar calendar = Calendar.getInstance();
		
		if(accountDTO.getDateCreated() != null){
			calendar.setTimeInMillis(accountDTO.getDateCreated().getTime());
			account.setDateCreated(accountDTO.getDateCreated());
		}
		
		if(accountDTO.getDateUpdated() != null){
			calendar.setTimeInMillis(accountDTO.getDateUpdated().getTime());
			account.setDateUpdated(accountDTO.getDateUpdated());
		}
		
		account.setEmail(accountDTO.getEmail());
		account.setFacebookId(accountDTO.getFacebookId());
		account.setFirstName(accountDTO.getFirstName());
		account.setLastName(accountDTO.getLastName());
		
		Account newAccount = accountRepository.save(account);
		
		accountDTO.setAccountId(newAccount.getAccountId());
		
		Utils.saveProfilePictureToServer(accountDTO);
		
		return accountDTO;
	}

}
