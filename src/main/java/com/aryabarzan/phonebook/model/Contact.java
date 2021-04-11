package com.aryabarzan.phonebook.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection = "contact")
public class Contact {
    @Transient
    public static final String SEQUENCE_NAME = "contact_sequence";

    @Id
    private long id;

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    @Indexed(unique = true)
    private String lastName;

    @NotBlank
    @Email(message = "Email should be valid!", regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,10}$")
    @Size(max = 100)
    @Indexed(unique = true)
    private String emailId;

    @Pattern(message = "Email should be valid!", regexp = "^\\(\\+[1-9]{1,3}\\)[0-9]{4,14}$")
    private String phoneNumber;

    private String githubName;

    private List<String> githubNameRepositories;

}
