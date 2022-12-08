package com.example.JWTSecure.service;
import com.example.JWTSecure.DTO.ChangeSlotDTO;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.domain.Slot;
import java.util.List;

public interface SlotService {
    List<Slot> getSlot();
    ResponseStatus updateSlot(ChangeSlotDTO changeSlotDTO);
}
