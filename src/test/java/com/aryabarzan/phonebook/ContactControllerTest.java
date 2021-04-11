package com.aryabarzan.phonebook;

import com.aryabarzan.phonebook.controller.ContactController;
import com.aryabarzan.phonebook.model.Contact;
import com.aryabarzan.phonebook.service.ContactService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureWebTestClient
public class ContactControllerTest {

    private static final String API_PATH= ContactController.API_PATH;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ContactService contactService;



    @Test
    public void _1_createContact() {
        Contact contact=new Contact();
        contact.setFirstName("Nader");
        contact.setLastName("Aryabarzan");
        contact.setPhoneNumber("(+98)9207239010");
        contact.setEmailId("aryabarzan@gmail.com");
        contact.setGithubName("aryabarzan");

        WebTestClient.BodyContentSpec result=webTestClient.post().uri(API_PATH)
                .body(Mono.just(contact), Contact.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("Nader")
                .jsonPath("$.githubNameRepositories[0]").isEqualTo("DBSCANPlot")
        ;
    }

    @Test
    public void _2_createAnotherContact() {
        Contact contact=new Contact();
        contact.setFirstName("Matti");
        contact.setLastName("Luukkainen");
        contact.setPhoneNumber("(+98)9128000");
        contact.setEmailId("mluukkai@iki.fi");
        contact.setGithubName("mluukkai");

        webTestClient.post().uri(API_PATH)
                .body(Mono.just(contact), Contact.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.emailId").isEqualTo("mluukkai@iki.fi")
        ;
    }

    @Test
    public void _3_updateContact() {
        Contact contact= contactService.getByLastname("Luukkainen");
        long contactId=contact.getId();
        contact.setFirstName("Nader");
        contact.setGithubName("aryabarzan");

        webTestClient.put().uri(API_PATH+"/"+contactId)
                .body(Mono.just(contact), Contact.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("Nader")
                .jsonPath("$.githubNameRepositories[0]").isEqualTo("DBSCANPlot")
        ;
    }
}
