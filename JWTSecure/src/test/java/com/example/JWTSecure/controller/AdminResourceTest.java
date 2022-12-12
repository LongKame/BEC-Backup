package com.example.JWTSecure.controller;

import com.example.JWTSecure.DTO.ChangeSlot;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.domain.Slot;
import com.example.JWTSecure.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminResourceTest {

    private final SlotService slotService;

    @Test
    @PutMapping("/update_slot")
    public ResponseEntity<ResponseStatus> updateSlot(ChangeSlot changeSlotDTO) {
        return ResponseEntity.ok().body(slotService.updateSlot(changeSlotDTO));
    }

    @Test
    @GetMapping("/get_slot")
    public ResponseEntity<List<Slot>> getSlot() {
        return ResponseEntity.ok().body(slotService.getSlot());
    }

}
