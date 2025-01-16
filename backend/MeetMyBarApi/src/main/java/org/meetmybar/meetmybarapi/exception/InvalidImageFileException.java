package org.meetmybar.meetmybarapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidImageFileException extends RuntimeException {
    public InvalidImageFileException() {
        super("Le fichier doit être une image");
    }
}