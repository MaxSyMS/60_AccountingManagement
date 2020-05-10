package telran.accounting.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Account {
	@Id
	String username;
	String hashCode;
	LocalDateTime activationDate;
	boolean revoked = false;
	HashSet<String> roles=new HashSet<>();
	LinkedList<String> lastHashCodes = new LinkedList<>();
	
	public Account() {}

	public Account(String username, String hashCode, LocalDateTime activationDate) {
		super();
		this.username = username;
		this.hashCode = hashCode;
		this.activationDate = activationDate;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public LocalDateTime getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(LocalDateTime activationDate) {
		this.activationDate = activationDate;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public HashSet<String> getRoles() {
		return roles;
	}

	public void setRoles(HashSet<String> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public LinkedList<String> getLastHashCodes() {
		return lastHashCodes;
	}
	
	public boolean addToLastHashCodes(String hashPassword){
		return lastHashCodes.add(hashPassword);
	}
	
	public boolean addRole(String role) {
		return roles.add(role);
	}
	
}
