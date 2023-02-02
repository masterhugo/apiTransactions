package co.neoris.apitransactions.repository;

import co.neoris.apitransactions.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

    @Query("SELECT t FROM TransactionEntity t WHERE t.transactionAccount = ?1")
    List<TransactionEntity> findTransactionsByAccount(Integer accountId);
}
