package com.example.JWTSecure.service.impl;
import com.example.JWTSecure.DTO.ChangeSlot;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.domain.ClassSchedule;
import com.example.JWTSecure.domain.Slot;
import com.example.JWTSecure.repo.ClassScheduleRepo;
import com.example.JWTSecure.repo.SlotRepo;
import com.example.JWTSecure.repo.impl.ClassScheduleCustomRepo;
import com.example.JWTSecure.service.SlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SlotServiceImpl implements SlotService {

    private final SlotRepo slotRepo;
    private final ClassScheduleRepo classScheduleRepo;
    private final ClassScheduleCustomRepo classScheduleCustomRepo;

    @Override
    public List<Slot> getSlot() {
        try{
            return slotRepo.findAll();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public ResponseStatus updateSlot(ChangeSlot changeSlotDTO) {

        ResponseStatus responseStatus = new ResponseStatus();
        ClassSchedule classSchedule = new ClassSchedule();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(changeSlotDTO.getDate(), formatter);
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(timeStamp, formatter1);

        if(classScheduleCustomRepo.findSlotEmpty(changeSlotDTO).size()==0){
            classSchedule.setId(changeSlotDTO.getClass_schedule_id());
//            classScheduleRepo.findById(changeSlotDTO.getClass_schedule_id()).get().getClassId();
            classSchedule.setClassId(classScheduleRepo.findById(changeSlotDTO.getClass_schedule_id()).get().getClassId());
            classSchedule.setSlotOfDate(Long.valueOf(changeSlotDTO.getSlot_of_date()));
            classSchedule.setSlot_th(changeSlotDTO.getSlot_th());
            classSchedule.setRoomId(changeSlotDTO.getRoom_id());
            classSchedule.setDate(localDate);
            classSchedule.setUpdatedAt(localDateTime);
            classScheduleRepo.save(classSchedule);
            responseStatus.setMessage("Successfully");
            responseStatus.setState(true);
            return responseStatus;
        }
        responseStatus.setMessage("Failure");
        responseStatus.setState(false);
        return responseStatus;
    }
}
