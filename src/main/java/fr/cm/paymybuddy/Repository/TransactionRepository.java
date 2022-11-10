package fr.cm.paymybuddy.Repository;

import fr.cm.paymybuddy.Model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository <Transaction,Long> {




}
