package ashwin.uomtrust.ac.mu.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ashwin.uomtrust.ac.mu.dto.AccountDTO;
import ashwin.uomtrust.ac.mu.entity.Account;



@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
		
		public Account findByAccountId(Long accountId);		
		public Account findByEmail(String email);
		
		@Query("select count(a) from Account a where DATE(a.dateCreated) = current_date and a.accountRole = 1")
		public int getTotalCarSeekerCreatedToday();
		
		@Query("select count(a) from Account a where DATE(a.dateCreated) = current_date and a.accountRole = 0")
		public int getTotalCarPoolerCreatedToday();
		
		@Query("select count(a) from Account a where a.accountRole <2")
		public int getTotalUser();
}
