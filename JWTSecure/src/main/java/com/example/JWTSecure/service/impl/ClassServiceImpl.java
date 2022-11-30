package com.example.JWTSecure.service.impl;

import com.example.JWTSecure.DTO.ClassDTO;
import com.example.JWTSecure.DTO.ClassScheduleDTO;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.DTO.SearchResultDTO;
import com.example.JWTSecure.domain.*;
import com.example.JWTSecure.repo.ClassRepo;
import com.example.JWTSecure.repo.ClassScheduleRepo;
import com.example.JWTSecure.repo.ClassSlotRepo;
import com.example.JWTSecure.repo.CourseRepo;
import com.example.JWTSecure.repo.impl.ClassCustomRepo;
import com.example.JWTSecure.service.ClassService;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClassServiceImpl implements ClassService {

    private final ClassCustomRepo classCustomRepo;
    private final ClassRepo classRepo;
    private final ClassSlotRepo classSlotRepo;
    private final CourseRepo courseRepo;
    private final ClassScheduleRepo classScheduleRepo;


    @Override
    public SearchResultDTO<ClassDTO> getAllClass(ClassDTO classDTO) {
        List<ClassDTO> dataResult;
        SearchResultDTO<ClassDTO> searchResult = new SearchResultDTO<>();
        try {
            Integer totalRecord = classCustomRepo.getTotal(classDTO).size();
            dataResult = classCustomRepo.doSearch(classDTO);
            if (dataResult != null && !dataResult.isEmpty()) {
                searchResult.setCode("0");
                searchResult.setSuccess(true);
                searchResult.setTitle("Success");
                searchResult.setMessage("Success");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                for (ClassDTO i : dataResult) {
                    try {
                        String sDate = i.getStart_date();
                        Date sParseDate = sdf.parse(sDate);
                        i.setStart_date(sdf1.format(sParseDate));
                        String eDate = i.getEnd_date();
                        Date eParseDate = sdf.parse(eDate);
                        i.setEnd_date(sdf1.format(eParseDate));
                    } catch (Exception e) {

                    }
                }
                searchResult.setResultData(dataResult);
                searchResult.setTotalRecordNoLimit(totalRecord);
            } else {
                searchResult.setCode("0");
                searchResult.setSuccess(false);
                searchResult.setTitle("Failure");
                searchResult.setMessage("Failure");
                searchResult.setResultData(Collections.emptyList());
                searchResult.setTotalRecordNoLimit(0);
            }
            return searchResult;
        } catch (Exception e) {
            searchResult.setCode("0");
            searchResult.setSuccess(false);
            searchResult.setTitle("Failure");
            searchResult.setMessage("Failure");
            searchResult.setResultData(Collections.emptyList());
            searchResult.setTotalRecordNoLimit(0);
            return searchResult;
        }
    }

    @Override
    public ResponseStatus addClass(ClassDTO classDTO) {
        Classes classes = new Classes();
        ClassSlot classSlot = new ClassSlot();
        ResponseStatus rs = new ResponseStatus();
        StringBuilder message = new StringBuilder();

        try {
            if (classDTO != null) {
                classDTO.setActive(true);
                if (classRepo.findByName(classDTO.getClass_name()) != null) {
                    message.append("Class ");
                }
                if (classCustomRepo.checkRoomActive(classDTO) != null) {
                    message.append("Room ");
                }
                if (classCustomRepo.checkTeacherTeaching(classDTO) != null) {
                    message.append("Teacher ");
                }
                if (!StringUtils.isBlank(message)) {
                    message.append("is duplicated");
                    rs.setMessage(message.toString());
                    rs.setState(false);
                    return rs;
                }
                if (classCustomRepo.checkRoomActive(classDTO) == null && classCustomRepo.checkTeacherTeaching(classDTO) == null) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(classDTO.getStart_date(), dtf);
                    LocalDate next2Week = localDate.plus(courseRepo.findById(classDTO.getCourse_id()).get().getNumberSlot() / 2 - 1, ChronoUnit.WEEKS);
                    DayOfWeek dayOfWeek1 = DayOfWeek.of(classDTO.getFirstOnWeek().getValue());
                    DayOfWeek dayOfWeek2 = DayOfWeek.of(classDTO.getSecondOnWeek().getValue());
                    DayOfWeek range = dayOfWeek2.minus(dayOfWeek1.getValue());
                    System.out.println("Between first slot and second slot: " + range.getValue());
                    LocalDate lastDate = next2Week.plus(range.getValue(), ChronoUnit.DAYS);
                    System.out.println("End date: " + dtf.format(lastDate));
                    classDTO.setEnd_date(lastDate.toString());

                    classes.setRoomId(classDTO.getRoom_id());
                    classes.setTeacherId(classDTO.getTeacher_id());
                    classes.setName(classDTO.getClass_name());
                    classes.setCourseId(classDTO.getCourse_id());
                    classes.setActive(classDTO.isActive());
                    classRepo.save(classes);

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dateS = formatter.parse(classDTO.getStart_date());
                        Date dateE = formatter.parse(classDTO.getEnd_date());
                        classes.setStartDate(dateS);
                        classes.setEndDate(dateE);
                    } catch (ParseException ex) {

                    }
                    classSlot.setSlotId(classDTO.getSlot_id());
                    classSlot.setClassId(classRepo.findTopByOrderByIdDesc().getId());
                    classSlot.setFirstOfWeek(dayOfWeek1.getValue());
                    classSlot.setSecondOfWeek(dayOfWeek2.getValue());
                    classSlotRepo.save(classSlot);

                    LocalDate localDate1 = LocalDate.parse(classDTO.getStart_date(), dtf);
                    LocalDate localDate2 = LocalDate.parse(classDTO.getStart_date(), dtf);
                    DayOfWeek range1 = dayOfWeek2.minus(dayOfWeek1.getValue());
                    int j = 1;
                    for (int i = 0; i < courseRepo.findById(classDTO.getCourse_id()).get().getNumberSlot() / 2; i++) {
                        localDate2 = localDate1.plus(i, ChronoUnit.WEEKS);
                        classScheduleRepo.save(new ClassSchedule(null, classRepo.findTopByOrderByIdDesc().getId(), j, localDate2, classDTO.getRoom_id(), classDTO.getSlot_id(), null));
                        j = (i + 1) * 2;
                        classScheduleRepo.save(new ClassSchedule(null, classRepo.findTopByOrderByIdDesc().getId(), j, localDate2.plus(range1.getValue(), ChronoUnit.DAYS), classDTO.getRoom_id(), classDTO.getSlot_id(), null));
                        j = j + 1;
                    }

                    rs.setMessage("Ok");
                    rs.setState(true);
                }
            } else {
                rs.setMessage("Failure");
                rs.setState(false);
            }
            return rs;
        } catch (Exception ex) {
            rs.setMessage("Failure");
            rs.setState(false);
            return rs;
        }
    }

    @Override
    public ResponseStatus disableClass(Long id) {
        ResponseStatus responseStatus = new ResponseStatus();
        try {
            classRepo.deActive(id);
            responseStatus.setState(true);
            responseStatus.setMessage("Success");
        } catch (Exception ex) {
            responseStatus.setState(false);
            responseStatus.setMessage("Failure");
        }
        return responseStatus;
    }

    @Override
    public List<ClassDTO> getTotalClass() {
        List<ClassDTO> list = new ArrayList<>();
        try {
            list = classCustomRepo.getTotalClass();
        } catch (Exception ex) {
        }
        return list;
    }

    @Override
    public List<ClassScheduleDTO> getClassById(Long class_id) {
        List<ClassScheduleDTO> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            list = classCustomRepo.getScheduleById(class_id);
            for (ClassScheduleDTO i : list) {
                String sDate = i.getDate_study();
                Date sParseDate = sdf.parse(sDate);
                i.setDate_study(sdf1.format(sParseDate));
            }
        } catch (Exception e) {

        }
        return list;
    }
}