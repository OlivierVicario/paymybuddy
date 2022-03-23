package com.paymybuddy.testing.service;



import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.paymybuddy.model.BankAccount;
import com.paymybuddy.model.BankTransfert;
import com.paymybuddy.model.User;
import com.paymybuddy.repository.BankAccountRepository;
import com.paymybuddy.repository.BankTransfertRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(true)
public class BankTransfertTests {
	
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private BankTransfertRepository bankTransfertRepo;

	@Autowired
	private BankAccountRepository bankAccountRepo;
	
	@Test
	public void testCreateBankTransfert() {
		
		User user = new User();
		BankAccount bankAccount = new BankAccount();
		bankAccount.setUser(user);
		bankAccountRepo.save(bankAccount);
		BankTransfert bankTransfert = new BankTransfert();
		bankTransfert.setUser(bankAccount.getUser());
		bankTransfert.setAmount(new BigDecimal(1000));
		BankTransfert savedBankTransfert = bankTransfertRepo.save(bankTransfert);

		BankTransfert existBankTransfert = entityManager.find(BankTransfert.class, savedBankTransfert.getBankTransfertId());

		assertThat(savedBankTransfert.getAmount()).isEqualTo(existBankTransfert.getAmount());
	}

	public BankAccountRepository getBankAccountRepo() {
		return bankAccountRepo;
	}

	public void setBankAccountRepo(BankAccountRepository bankAccountRepo) {
		this.bankAccountRepo = bankAccountRepo;
	}

	public BankTransfertRepository getBankTransfertRepo() {
		return bankTransfertRepo;
	}

	public void setBankTransfertRepo(BankTransfertRepository bankTransfertRepo) {
		this.bankTransfertRepo = bankTransfertRepo;
	}
}


