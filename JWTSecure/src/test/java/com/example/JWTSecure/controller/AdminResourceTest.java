package com.example.JWTSecure.controller;

import com.example.JWTSecure.service.SlotService;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminResourceTest {
//    private final SlotService slotService;
    public AdminResourceTest() {
    }

    @Test
    @PutMapping("/update_slot")
    public void updateSlot() {
        ResponseEntity.ok().body("");
    }

    @Test
    @GetMapping("/get_slot")
    public void getSlot() {
        ResponseEntity.ok().body("");
    }

}
