package org.meetmybar.meetmybarapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PhotoFileNotFoundException extends RuntimeException {
    public PhotoFileNotFoundException(String urlFile) {
        super("Fichier photo non trouvé: " + urlFile);
    }
}