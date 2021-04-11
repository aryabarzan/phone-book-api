package com.aryabarzan.phonebook.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class ClientRestErrorException extends Exception {

    public void throwRestErrorException(Exception e) {
        try {
            Pattern compile = Pattern.compile("\"message\"\\s*:\\s*\"([^\"]+)\",?");
            Matcher matcher = compile.matcher(e.getMessage());
            boolean find = matcher.find();
            String message = null;
            if (find) message = matcher.group(1);

            throw new RestErrorException.Builder()
                    .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage(message == null ? e.getMessage() : message)
                    .setDeveloperMessage(message == null ? e.getMessage() : message)
                    .build();
        } catch (Exception exception) {
            throw new RestErrorException.Builder()
                    .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("General Error")
                    .setDeveloperMessage(exception.getMessage())
                    .build();

        }
    }
}
