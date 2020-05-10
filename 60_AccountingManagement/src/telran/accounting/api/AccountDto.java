package telran.accounting.api;

import java.util.HashSet;

public class AccountDto {
	public String userName;
	public String password;
	public HashSet<String> roles;
	public AccountDto(String userName, String password, HashSet<String> roles) {
		super();
		this.userName = userName;
		this.password = password;
		this.roles = roles;
	}
	
	public AccountDto() {}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public HashSet<String> getRoles() {
		return roles;
	}
	
	
}
