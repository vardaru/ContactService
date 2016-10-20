package tr.com.logo.repositories;

import org.springframework.data.repository.CrudRepository;
import tr.com.logo.model.Client;

import java.util.Optional;

/**
 * Created by umitvardar on 16.08.2016.
 */
public interface ClientRepository extends CrudRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
