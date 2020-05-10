package telran.accounting.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.accounting.entities.Account;

public interface AccountsRepository extends MongoRepository<Account, String> {
}
