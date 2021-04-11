package com.aryabarzan.phonebook.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GithubRepository {
    private Long id;
    private String name;
    private String full_name;
    private String description;
    private String isPrivate;
    private String isFork;
    private String url;
    private String htmlUrl;
    private String gitUrl;
    private Long forksCount;
    private Long stargazersCount;
    private Long watchersCount;
}
