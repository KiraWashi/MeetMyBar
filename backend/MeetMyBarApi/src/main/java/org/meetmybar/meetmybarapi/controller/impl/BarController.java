package org.meetmybar.meetmybarapi.controller.impl;

import org.meetmybar.meetmybarapi.controller.api.DefaultApi;
import org.meetmybar.api.model.Bar;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BarController implements DefaultApi {


    @Override
    public ResponseEntity<Bar> deleteBar(Bar bar) {
        // TODO: Implement delete logic
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<Bar>> getBar() {
        // TODO: Implement get all bars logic
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Bar> getBarByAddress(Object barAddress) {
        // TODO: Implement get bar by address logic
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Bar> getBarByName(Object barName) {
        // TODO: Implement get bar by name logic
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Bar> modifyBar(Bar bar) {
        // TODO: Implement update logic
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Bar> addBar(Bar bar) {
        // TODO: Implement create logic
        return ResponseEntity.ok().build();
    }
}