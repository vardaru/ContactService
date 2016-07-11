package tr.com.logo.repositories;

import org.springframework.data.repository.CrudRepository;
import tr.com.logo.model.Contact;

/**
 * Created by umitvardar on 30.06.2016.
 */
public interface ContactRepository extends CrudRepository<Contact, String> {
}
