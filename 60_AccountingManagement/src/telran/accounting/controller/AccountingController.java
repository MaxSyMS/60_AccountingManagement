package telran.accounting.controller;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.accounting.api.AccountDto;
import telran.accounting.api.AccountingApiConstants;
import telran.accounting.api.AccountingCodes;
import telran.accounting.interfaces.IAccountingManagement;

@RestController
public class AccountingController {

	@Autowired
	IAccountingManagement accounting;
	
	@PostMapping(value=AccountingApiConstants.ADD_ACCOUNT)
	AccountingCodes addAccount(@RequestBody AccountDto account)
	{
		return accounting.addAccount(account);
	}
	
	@DeleteMapping(value = AccountingApiConstants.REMOVE_ACCOUNT)
	AccountingCodes removeAccount(@RequestParam(name="userName") String userName) {
		return accounting.removeAccount(userName);
	}
	
	@PutMapping(value = AccountingApiConstants.UPDATE_PASSWORD)
	AccountingCodes updatePassword(@RequestParam(name="userName")String userName,
			@RequestParam(name="password")String password) {
		return accounting.updatePassword(userName, password);
	}
	
	@PutMapping(value = AccountingApiConstants.REVOKE_ACCOUNT)
	AccountingCodes revokeAccount(@RequestParam(name="userName") String userName) {
		return accounting.revokeAccount(userName);
	}
	
	@PutMapping(value = AccountingApiConstants.ACTIVATE_ACCOUNT)
	AccountingCodes activateAccount(@RequestParam(name="userName") String userName) {
		return accounting.activateAccount(userName);
	}
	
	@GetMapping(value = AccountingApiConstants.GET_PASSWORD_HESH)
	String getPasswordHash(String userName) {
		return accounting.getPasswordHash(userName);
	}
	
	@GetMapping(value = AccountingApiConstants.GET_ACTIVATION_DATE)
	LocalDateTime getActivationDate(String userName) {
		return accounting.getActivationDate(userName);
	}
	
	@GetMapping(value = AccountingApiConstants.GET_ROLES)
	HashSet<String> getRoles(String userName){
		return accounting.getRoles(userName);
	}
	
	@PutMapping(value = AccountingApiConstants.ADD_ROLE)
	AccountingCodes addRole(@RequestParam(name="userName")String userName,
			@RequestParam(name="role")String role) {
		return accounting.addRole(userName, role);
	}
	
	@DeleteMapping(value = AccountingApiConstants.REMOVE_ROLE)
	AccountingCodes removeRole(@RequestParam(name="userName")String userName,
			@RequestParam(name="role")String role){
		return accounting.removeRole(userName, role);
	}
	
	
	
	
	
	
	
	
	
	
	
}
