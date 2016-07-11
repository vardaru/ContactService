package tr.com.logo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.logo.model.Contact;
import tr.com.logo.repositories.ContactRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umitvardar on 30.06.2016.
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(value = "http://localhost:4200")
public class ContactController {

    @Autowired
    ContactRepository repository;

    Logger logger = LoggerFactory.getLogger("ContactController");

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Contact> findAll() {
        logger.info("GET request received");
        Iterable<Contact> all = repository.findAll();
        ArrayList list = new ArrayList();
        for (Contact contact : all) {
            list.add(contact);
        }
        return list;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Contact contact) {
        logger.info("POST request received");
        try {
            if (contact.getName().isEmpty() || contact.getSurname().isEmpty() || contact.getPhone().isEmpty()) {
                return ResponseEntity.badRequest().build(); //400
            }
            if (contact.getId() != null && contact.getId().trim().isEmpty())
                contact.setId(null);
            repository.save(contact);
            logger.info("saved. current db:");
            repository.findAll().forEach(c -> System.out.println(c.toString()));
            return ResponseEntity.noContent().build(); //204
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") String id) {
        repository.delete(id);
        logger.info("deleted: " + id);
        return ResponseEntity.noContent().build();
    }

}
