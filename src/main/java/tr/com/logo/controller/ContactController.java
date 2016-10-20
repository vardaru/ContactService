package tr.com.logo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.logo.model.Contact;
import tr.com.logo.repositories.ContactRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by umitvardar on 30.06.2016.
 */
@RestController
@RequestMapping("/api/v1")
//@CrossOrigin(value = "http://localhost:4200", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE})
public class ContactController {

    @Autowired
    ContactRepository repository;

    Logger logger = LoggerFactory.getLogger("ContactController");

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @CrossOrigin(value = "http://localhost:4200")
    public HttpEntity<List<Contact>> findAll() {
        logger.info("GET request received");
        Iterable<Contact> all = repository.findAll();
        ArrayList list = new ArrayList();
        for (Contact contact : all) {
            contact.add(linkTo(methodOn(ContactController.class).getContact(contact.getContactId())).withSelfRel());
            list.add(contact);
        }
        return new ResponseEntity<List<Contact>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/contact/{contactId}", method = RequestMethod.GET)
    @CrossOrigin(value = "http://localhost:4200")
    public HttpEntity<Contact> getContact(@PathVariable String contactId){
        Contact contact =  repository.findOne(contactId);
        if(contact != null) {
            contact.add(linkTo(methodOn(ContactController.class).getContact(contactId)).withSelfRel());
            return new ResponseEntity<Contact>(contact, HttpStatus.OK);
        }else{
            return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @CrossOrigin(value = "http://localhost:4200")
    public ResponseEntity save(@RequestBody Contact contact) {
        logger.info("POST request received");
        try {
            if (contact.getName().isEmpty() || contact.getSurname().isEmpty() || contact.getPhone().isEmpty()) {
                return ResponseEntity.badRequest().build(); //400
            }
            if (contact.getId() != null && contact.getContactId().trim().isEmpty())
                contact.setContactId(null);
            contact = repository.save(contact);
            logger.info("saved. current db:");
            repository.findAll().forEach(c -> System.out.println(c.toString()));
            return new ResponseEntity(contact,HttpStatus.CREATED); //204
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @CrossOrigin(value = "http://localhost:4200")
    public ResponseEntity delete(@PathVariable("id") String id) {
        repository.delete(id);
        logger.info("deleted: " + id);
        return ResponseEntity.noContent().build();
    }

}
