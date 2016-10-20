package tr.com.logo.repositories;

import org.springframework.data.repository.CrudRepository;
import tr.com.logo.model.User;

import java.util.Optional;

/**
 * Created by umitvardar on 8.08.2016.
 */
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByUname(String uname);
}
