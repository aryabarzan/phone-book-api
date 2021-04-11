package com.aryabarzan.phonebook.controller;

import com.aryabarzan.phonebook.exception.EntityNotFoundException;
import com.aryabarzan.phonebook.model.Contact;
import com.aryabarzan.phonebook.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ContactController.API_PATH)
public class ContactController {

    public static final String API_PATH="/api/v1/contacts/";

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;


    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }


    @GetMapping("/")
    public List<Contact> getAllContacts() {
        return contactService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") Long contactId) throws ResourceNotFoundException {
        try {
            Contact contact = contactService.getById(contactId);
            return ResponseEntity.status(HttpStatus.OK).body(contact);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Contact not found for this id :: " + contactId);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        try {
            Contact contactNew = contactService.create(contact);
            return ResponseEntity.status(HttpStatus.CREATED).body(contactNew);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable(value = "id") Long contactId,
                                                 @Valid @RequestBody Contact contactDetails) throws ResourceNotFoundException {
        try {
            Contact updatedContact = contactService.update(contactId, contactDetails);
            return ResponseEntity.status(HttpStatus.OK).body(updatedContact);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Contact not found for this id :: " + contactId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Contact> deleteContact(@PathVariable(value = "id") Long contactId) throws ResourceNotFoundException {
        try {
            contactService.delete(contactId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Contact not found for this id :: " + contactId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException ex) {
        logger.error("Error from WebClient - Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString(), ex);
        return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
    }

}
