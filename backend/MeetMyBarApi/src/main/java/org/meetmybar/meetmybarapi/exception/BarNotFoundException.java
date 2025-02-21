package org.meetmybar.meetmybarapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BarNotFoundException extends RuntimeException {
    public BarNotFoundException(String id) {
        super("Bar non trouvée avec l'ID: " + id);
    }
}