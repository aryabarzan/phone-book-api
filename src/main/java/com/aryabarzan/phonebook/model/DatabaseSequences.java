package com.aryabarzan.phonebook.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document(collection = "database_sequences")
public class DatabaseSequences {
    @Id
    private String id;
    private long seqValue;
}


