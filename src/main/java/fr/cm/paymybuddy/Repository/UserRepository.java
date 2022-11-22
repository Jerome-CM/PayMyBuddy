package fr.cm.paymybuddy.Repository;

import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.Model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository <User,Long> {

    public User findByMail(String mail);

    @Query(value="INSERT INTO Relations_Users(id_user, id_friend, date_relation) VALUES (?1, ?2, NOW())", nativeQuery = true)
    public void addFriend(long id, long idFriend);

    @Transactional
    @Modifying
    @Query(value ="UPDATE Users SET account_balance=?1, date_modification=NOW() WHERE mail=?2", nativeQuery = true)
    public void updateAccountBalance(double amount, String mail);
}
