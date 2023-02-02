package co.neoris.apitransactions.repository;

import co.neoris.apitransactions.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Integer> {

    @Query("SELECT a FROM AccountEntity a WHERE a.accountNumber = ?1 AND a.accountType = ?2 AND a.accountOwner = ?3")
    Optional<AccountEntity> findAccountByAccountNumberAAndAccountTypeAAndAccountOwner(String accountNumber, String accountType, Integer accountOwner);
}
