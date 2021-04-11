package com.aryabarzan.phonebook.service;

import com.aryabarzan.phonebook.config.AppProperties;
import com.aryabarzan.phonebook.payload.GithubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class GithubService {
    private static final String GITHUB_V3_MIME_TYPE = "application/vnd.github.v3+json";
    private static final String GITHUB_API_BASE_URL = "https://api.github.com";
    private static final String USER_AGENT = "Spring 5 WebClient";
    private static final Logger logger = LoggerFactory.getLogger(GithubService.class);
    private final WebClient webClient;

    @Autowired
    public GithubService(AppProperties appProperties) {
        this.webClient = WebClient.builder().baseUrl(GITHUB_API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, GITHUB_V3_MIME_TYPE)
                .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
                .filter(logRequest()).build();
    }

    public List<String> getRepositories(String userName) {
        Flux<GithubRepository> apiResult = webClient.get().uri("/users/{userName}/repos?sort={sortField}&direction={sortDirection}", userName, "updated", "desc")
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(GithubRepository.class));
        List<GithubRepository> repositoriesObjects = apiResult.collectList().block();

        List<String> repositoriesNames = repositoriesObjects.stream().map(githubRepository -> githubRepository.getName()).collect(Collectors.toList());

        return repositoriesNames;
    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            logger.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
            return next.exchange(clientRequest);
        };
    }
}
