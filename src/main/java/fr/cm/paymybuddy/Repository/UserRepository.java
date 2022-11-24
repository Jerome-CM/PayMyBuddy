package fr.cm.paymybuddy.Repository;

import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.Model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository <User,Long> {


    public User findByMail(String mail);

    @Query(value="SELECT * FROM users JOIN users_friends ON users_friends.friends_id = users.id WHERE user_id = ?1", nativeQuery = true)
    public List<User> getMyFriends(long id);

    @Transactional
    @Modifying
    @Query(value ="UPDATE users SET account_balance=?1, date_modification=NOW() WHERE mail=?2", nativeQuery = true)
    public void updateAccountBalance(double amount, String mail);
    

}
