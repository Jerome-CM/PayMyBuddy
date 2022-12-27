package fr.cm.paymybuddy.Config.Security;

import fr.cm.paymybuddy.Config.Generated;
import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);
    private UserRepository userRepository;


    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *
     * @param mail : is the input in the login form, map to username for Spring with .usernameParameter("mail") in the filterChain
     * @return User Spring
     * @throws UsernameNotFoundException
     */
    @Generated
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

            User user = userRepository.findByMail(mail);
            if (user == null) {
                throw new UsernameNotFoundException(mail);
            }
            /* load mail, password and Authorities in a User Spring */
            UserDetails userAuth = org.springframework.security.core.userdetails.User.withUsername(user.getMail()).password(user.getPassword()).authorities(user.getRole().toString()).build();
            logger.info("Connexion User : {}", userAuth);
            return userAuth;
        }
}
