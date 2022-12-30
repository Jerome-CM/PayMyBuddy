package fr.cm.paymybuddy.Repository;

import fr.cm.paymybuddy.Model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository <Transaction,Long> {
    @Query(value="SELECT * FROM transaction INNER JOIN user ON transaction.user_friend_id=user.id WHERE user = ?1 ORDER BY transaction.id DESC LIMIT ?2,?3", nativeQuery = true)
    public List<Transaction> findByIdUser(long id, int limitStart, int limitEnd);

    public List<Transaction> findAllByUserId(long id);
}
