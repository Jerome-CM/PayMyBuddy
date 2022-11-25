package fr.cm.paymybuddy.Repository;

import fr.cm.paymybuddy.DTO.TransactionDTO;
import fr.cm.paymybuddy.Model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository <Transaction,Long> {
@Query(value="SELECT * FROM transactions INNER JOIN users ON transactions.user_friend_id=users.id WHERE user = ?1", nativeQuery = true)
    public List<Transaction> findByIdUser(long id);


}
