package com.aryabarzan.phonebook.service;

import com.aryabarzan.phonebook.exception.EntityNotFoundException;
import com.aryabarzan.phonebook.model.Contact;
import com.aryabarzan.phonebook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final GithubService githubService;

    @Autowired
    public ContactService(ContactRepository contactRepository, SequenceGeneratorService sequenceGeneratorService, GithubService githubService) {
        this.contactRepository = contactRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.githubService = githubService;
    }

    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    public Contact getById(Long contactId) throws EntityNotFoundException {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException());
        return contact;
    }

    public Contact getByLastname(String lastName) throws EntityNotFoundException {
        Contact contact = contactRepository.findByLastName(lastName);
        if (contact == null) throw new EntityNotFoundException();
        return contact;
    }

    public Contact create(Contact contact) throws Exception {
        contact.setId(sequenceGeneratorService.generateSequence(Contact.SEQUENCE_NAME));
        contact.setGithubNameRepositories(this.githubService.getRepositories(contact.getGithubName()));
        return contactRepository.save(contact);
    }

    public Contact update(Long contactId, Contact contactDetails) throws EntityNotFoundException, Exception {
        Contact contact = this.getById(contactId);

        //If githubName is changed.
        if (!contact.getGithubName().equalsIgnoreCase(contactDetails.getGithubName())) {
            contact.setGithubNameRepositories(this.githubService.getRepositories(contactDetails.getGithubName()));
        }
        contact.setFirstName(contactDetails.getFirstName());
        contact.setLastName(contactDetails.getLastName());
        contact.setEmailId(contactDetails.getEmailId());
        contact.setPhoneNumber(contactDetails.getPhoneNumber());
        contact.setGithubName(contactDetails.getGithubName());

        final Contact updatedContact = contactRepository.save(contact);
        return updatedContact;
    }

    public void delete(Long contactId)
            throws EntityNotFoundException, Exception {
        Contact contact = this.getById(contactId);
        contactRepository.delete(contact);
    }

}
