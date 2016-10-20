package tr.com.logo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.logo.model.User;
import tr.com.logo.repositories.UserRepository;

/**
 * Created by umitvardar on 8.08.2016.
 */

@Service
public class ContactUserDetailsService  implements UserDetailsService{
    private static final Logger logger = LoggerFactory.getLogger(ContactUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String uname) throws UsernameNotFoundException {
        User user = userRepository.findByUname(uname)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with uname=%s was not found", uname)));;
        return new ContactUserDetails(user);
    }

}
