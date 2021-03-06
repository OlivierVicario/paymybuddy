package com.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.BankAccount;
import com.paymybuddy.model.User;
import com.paymybuddy.repository.BankAccountRepository;

@Service
public class BankAccountService {
	
	@Autowired
	private BankAccountRepository bankAccountRepo;
	public String update(BankAccount bankAccount, User user) {
		
		bankAccount.setUser(user);
		bankAccountRepo.save(bankAccount);
		return "success";
	}
	
	public BankAccount findByUser(User user) {
		return bankAccountRepo.findByUser(user);
	}
	
}
