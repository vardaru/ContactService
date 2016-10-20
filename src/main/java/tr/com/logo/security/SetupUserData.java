package tr.com.logo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import tr.com.logo.model.Privilege;
import tr.com.logo.model.User;
import tr.com.logo.repositories.PrivilegeRepository;
import tr.com.logo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by umitvardar on 8.08.2016.
 */
@Component
public class SetupUserData {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init(){
        initPrivileges();
        initUsers();
    }

    private void initPrivileges() {
        privilegeRepository.deleteAll();

        Privilege privilege = new Privilege("ADMIN");
        privilegeRepository.save(privilege);
    }

    private void initUsers() {
        userRepository.deleteAll();

        final Optional<Privilege> privilege =  privilegeRepository.findByName("ADMIN");
        Set<Privilege> privilegeSet = new HashSet();
        privilegeSet.add(privilege.get());

        User user = new User("vardaru", encoder.encode("vardaru"), "Ümit", "Vardar");
        user.setPrivileges(privilegeSet);
        userRepository.save(user);
    }
}
