package com.example.JWTSecure.service.impl;

import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.domain.Room;
import com.example.JWTSecure.repo.RoomRepo;
import com.example.JWTSecure.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepo roomRepo;

    @Override
    public ResponseStatus addRoom(Room room) {
        ResponseStatus responseStatus = new ResponseStatus();
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(timeStamp, formatter);
        try {
            if (room != null) {
                if (roomRepo.findByRoomname(room.getRoomname()) == null) {
                    room.setCreatedAt(localDateTime);
                    room.setUpdatedAt(localDateTime);
                    roomRepo.save(room);
                    responseStatus.setState(true);
                    responseStatus.setMessage("Add successful");
                } else {
                    responseStatus.setState(false);
                    responseStatus.setMessage("Room name is existed!");
                }
            }
            return responseStatus;
        } catch (Exception e) {
            responseStatus.setState(false);
            responseStatus.setMessage("Add failed");
            return responseStatus;
        }
    }

    @Override
    public ResponseStatus editRoom(Room room) {
        ResponseStatus responseStatus = new ResponseStatus();
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(timeStamp, formatter);
        try {
            if (room != null) {

                if (roomRepo.findById(room.getId()).get().getRoomname().equals(room.getRoomname())) {
                    room.setCreatedAt(roomRepo.findById(room.getId()).get().getCreatedAt());
                    room.setUpdatedAt(localDateTime);
                    roomRepo.save(room);
                    responseStatus.setState(true);
                    responseStatus.setMessage("Update successful");
                    return responseStatus;
                }
                if (roomRepo.findByRoomname(room.getRoomname()) == null) {
                    room.setCreatedAt(roomRepo.findById(room.getId()).get().getCreatedAt());
                    room.setUpdatedAt(localDateTime);
                    roomRepo.save(room);
                    responseStatus.setState(true);
                    responseStatus.setMessage("Update successful");
                    return responseStatus;
                }
                if (roomRepo.findByRoomname(room.getRoomname()) != null) {
                    room.setCreatedAt(roomRepo.findById(room.getId()).get().getCreatedAt());
                    room.setUpdatedAt(localDateTime);
                    roomRepo.save(room);
                    responseStatus.setState(false);
                    responseStatus.setMessage("Room is existed.");
                    return responseStatus;
                }
            } else {
                responseStatus.setState(false);
                responseStatus.setMessage("Update failed");
            }
            return responseStatus;
        } catch (Exception e) {
            responseStatus.setState(false);
            responseStatus.setMessage("Update failed");
            return responseStatus;
        }
    }

    @Override
    public ResponseStatus updateActiveRoom(Long id) {
        ResponseStatus responseStatus = new ResponseStatus();
        try {
            if (id != null) {
                roomRepo.updateActiveRoom(id);
                responseStatus.setState(true);
                responseStatus.setMessage("Update successful");
            } else {
                responseStatus.setState(false);
                responseStatus.setMessage("Update failed");
            }
            return responseStatus;
        } catch (Exception e) {
            responseStatus.setState(false);
            responseStatus.setMessage("Update failed");
            return responseStatus;
        }
    }
}
