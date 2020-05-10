package telran.accounting.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.accounting.api.AccountDto;
import telran.accounting.api.AccountingCodes;
import static telran.accounting.api.AccountingCodes.*;
import telran.accounting.entities.Account;
import telran.accounting.exceptions.UserNotExistException;
import telran.accounting.interfaces.IAccountingManagement;
import telran.accounting.repo.AccountsRepository;

@Service
public class AccountingMongo implements IAccountingManagement{
@Autowired
AccountsRepository accountsRepository;

@Autowired
PasswordEncoder encoder;

@Value("${password_length:5}")
private int passwordLength;



private Account accountExists(String userName) {
	return accountsRepository.findById(userName).orElse(null); 
}

@Override
public AccountingCodes addAccount(AccountDto account) {
	if(accountsRepository.existsById(account.userName)) {
		return ACCOUNT_ALREADY_EXISTS;
	}
	if(!isPasswordValid(account.password))
	{
		return WRONG_PASSWORD;
	}
	Account accountMongo = new Account(account.userName, createHashCode(account.password), 
			LocalDateTime.now());
	if(account.roles!=null) accountMongo.setRoles(account.roles);
	accountsRepository.save(accountMongo);
	return OK;
}

private String createHashCode(String password) {
	
	return encoder.encode(password);
}

private boolean isPasswordValid(String password) {
	
	return password.length()>=passwordLength;
}

@Override
public AccountingCodes removeAccount(String userName) {
	
	if (accountExists(userName) == null) {
		return ACCOUNT_NOT_EXISTS;
	}
		accountsRepository.deleteById(userName);
		
	return OK;
}

@Override
public AccountingCodes updatePassword(String userName, String password) {
	
	Account userAccount = accountExists(userName);
	
	if (userAccount == null) {
		return ACCOUNT_NOT_EXISTS;
	}
	
	if(!isPasswordValid(password))
	{
		return WRONG_PASSWORD;
	}
	
	if(matchPassword(password,userAccount.getHashCode())) {
		return SAME_PASSWORD;
	}
	
	LinkedList<String> usedPasswords = userAccount.getLastHashCodes();
	for (String hashPassword : usedPasswords) {
		if(matchPassword(password, hashPassword)) {
			return PASSWORD_ALREADY_USED;
		}
	}
		
	
	
	userAccount.addToLastHashCodes(userAccount.getHashCode());
	
	userAccount.setHashCode(createHashCode(password));
	
	userAccount.setActivationDate(LocalDateTime.now());
	
	accountsRepository.save(userAccount);
	
	return OK;
}

private boolean matchPassword(String password, String hashCode) {
	return encoder.matches(password, hashCode);
}

@Override
public AccountingCodes revokeAccount(String userName) {
	
	Account userAccount = accountExists(userName);
	
	if (userAccount == null) {
		return ACCOUNT_NOT_EXISTS;
	}
	
	userAccount.setRevoked(true);
	
	accountsRepository.save(userAccount);
	
	return ACCOUNT_REVOKED;
}

@Override
public AccountingCodes activateAccount(String userName) {

	Account userAccount = accountExists(userName);
	
	if (userAccount == null) {
		return ACCOUNT_NOT_EXISTS;
	}
	
	userAccount.setRevoked(false);
	
	userAccount.setActivationDate(LocalDateTime.now());
	
	accountsRepository.save(userAccount);
	
	return ACCOUNT_ACTIVATED;
}

@Override
public String getPasswordHash(String userName) {

	Account userAccount = accountExists(userName);
	
	if (userAccount == null) {
		throw new UserNotExistException();
	}
	
	return userAccount.getHashCode();
}

@Override
public LocalDateTime getActivationDate(String userName) {

	Account userAccount = accountExists(userName);
	
	if (userAccount == null) {
		throw new UserNotExistException();
	}
	
	return userAccount.getActivationDate();
}

@Override
public HashSet<String> getRoles(String userName) {

	Account userAccount = accountExists(userName);
	
	if (userAccount == null) {
		throw new UserNotExistException();
	}
	
	return userAccount.getRoles();
}

@Override
public AccountingCodes addRole(String userName, String role) {

	Account userAccount = accountExists(userName);
	
	if (userAccount == null) {
		return ACCOUNT_NOT_EXISTS;
	}
	
	if (!userAccount.addRole(role)) {
		return ROLE_ALREDY_EXISTS;
	}
	
	accountsRepository.save(userAccount);
	
	return OK;
}

@Override
public AccountingCodes removeRole(String userName, String role) {

	Account userAccount = accountExists(userName);
	
	if (userAccount == null) {
		return ACCOUNT_NOT_EXISTS;
	}
	
	if(!userAccount.getRoles().contains(role)) {
		return ROLE_NOT_EXISTS;
	}
	
	userAccount.getRoles().remove(role);
	
	accountsRepository.save(userAccount);
	
	return OK;
}


	
}
