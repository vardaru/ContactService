package tr.com.logo.repositories;

import org.springframework.data.repository.CrudRepository;
import tr.com.logo.model.Privilege;

import java.util.Optional;

/**
 * Created by umitvardar on 8.08.2016.
 */
public interface PrivilegeRepository extends CrudRepository<Privilege, String> {
    Optional<Privilege> findByName(String name);
}
