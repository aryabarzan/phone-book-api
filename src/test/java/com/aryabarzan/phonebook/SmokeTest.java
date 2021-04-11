package com.aryabarzan.phonebook;

import com.aryabarzan.phonebook.controller.ContactController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private ContactController contactController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(contactController).isNotNull();
    }

}
