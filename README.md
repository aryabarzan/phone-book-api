# Simple Springboot Phonebook API

## Requirements

For building and running the application you need:

- [JDK 16](https://openjdk.java.net/projects/jdk/16/)
- [Maven 3.6](https://maven.apache.org)
    - Before using phonebook api you should compile and add this project. You can use this command: `mvn clean install`.
- [MongoDB 4.4](https://www.mongodb.com/)
    - Create a database named *phone-book*. You can use bellow:
    ```
    $monog
    >use phone-book
    ```

    
## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.aryabarzan.phonebook.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## OpenAPI
After running thr App you can use this [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to find OpenAPI specification for the REST APIs
