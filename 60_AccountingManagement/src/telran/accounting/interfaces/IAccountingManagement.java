package telran.accounting.interfaces;

import java.time.LocalDateTime;
import java.util.HashSet;

import telran.accounting.api.AccountDto;
import telran.accounting.api.AccountingCodes;

public interface IAccountingManagement {
	AccountingCodes addAccount(AccountDto account);
	AccountingCodes removeAccount(String userName);
	AccountingCodes updatePassword(String userName, String password);
	AccountingCodes revokeAccount(String userName);
	AccountingCodes activateAccount(String userName);
	String getPasswordHash(String userName);
	LocalDateTime getActivationDate(String userName);
	HashSet<String> getRoles(String userName);
	AccountingCodes addRole(String userName, String role);
	AccountingCodes removeRole(String userName, String role);
}
