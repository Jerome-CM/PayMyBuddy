package fr.cm.paymybuddy.Config.Security;

import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserRepository userRepository;


    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        User user = userRepository.findByMail(mail);
        return (UserDetails) user;

    }
}
