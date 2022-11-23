package com.example.JWTSecure.service.impl;

import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.domain.Activity;
import com.example.JWTSecure.domain.Classes;
import com.example.JWTSecure.domain.Teacher;
import com.example.JWTSecure.domain.User;
import com.example.JWTSecure.repo.*;
import com.example.JWTSecure.repo.impl.TeacherCustomRepo;
import com.example.JWTSecure.repo.impl.TimeTableCustomRepo;
import com.example.JWTSecure.service.TeacherService;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {


    private final PasswordEncoder passwordEncoder;
    private final TeacherRepo teacherRepo;
    private final UserRepo userRepo;
    private final TeacherCustomRepo teacherCustomRepo;
    private final ActivityRepo activityRepo;
    private final ClassRepo classRepo;
    private final TimeTableCustomRepo timeTableCustomRepo;

    public SearchResultDTO<TeacherDTO> getAllTeacher(TeacherDTO productDTO) {
        List<TeacherDTO> dataResult;
        SearchResultDTO<TeacherDTO> searchResult = new SearchResultDTO<>();
        try {
            Integer totalRecord = teacherCustomRepo.getTotal(productDTO).size();
            dataResult = teacherCustomRepo.doSearch(productDTO);
            if (dataResult != null && !dataResult.isEmpty()) {
                searchResult.setCode("0");
                searchResult.setSuccess(true);
                searchResult.setTitle("Success");
                searchResult.setMessage("Success");
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
    public ResponseStatus addTeacher(AddTeacherDTO addTeacherDTO) {
        AddTeacherDTO dto = new AddTeacherDTO();
        User user = new User();
        Teacher teacher = new Teacher();

        ResponseStatus rs = new ResponseStatus();
        StringBuilder message = new StringBuilder();
        try {
            if (addTeacherDTO != null) {

                if (userRepo.findByUsername(addTeacherDTO.getUser_name()) != null) {
                    message.append("Username ");
                }
                if (userRepo.findByEmail(addTeacherDTO.getEmail()) != null) {
                    message.append("Email ");
                }
                if (userRepo.findByPhone(addTeacherDTO.getPhone()) != null) {
                    message.append("Phone ");
                }

                if (!StringUtils.isBlank(message)) {
                    message.append("is existed");
                    rs.setMessage(message.toString());
                    rs.setState(false);
                    return rs;
                }

                if (userRepo.findByUsername(addTeacherDTO.getUser_name()) == null
                        || userRepo.findByEmail(addTeacherDTO.getEmail()) == null
                        || userRepo.findByPhone(addTeacherDTO.getPhone()) == null) {
                    user.setUsername(addTeacherDTO.getUser_name());
                    user.setFullname(addTeacherDTO.getFull_name());
                    user.setPassword(passwordEncoder.encode(addTeacherDTO.getPassword()));
                    user.setEmail(addTeacherDTO.getEmail());
                    user.setPhone(addTeacherDTO.getPhone());
                    user.setAddress(addTeacherDTO.getAddress());
                    user.setActive(true);
                    userRepo.save(user);

                    teacher.setUserId(userRepo.findByUsername(addTeacherDTO.getUser_name()).getId());
                    teacher.setRoleId(3L);
                    teacher.setImageUrl(addTeacherDTO.getImageUrl());
                    teacherRepo.save(teacher);
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
    public TeacherDTO viewProfile(TeacherDTO teacherDTO) {
        try {
            return teacherCustomRepo.getTeacher(teacherDTO);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseStatus editTeacher(AddTeacherDTO addTeacherDTO) {
        User user = new User();
        Teacher teacher = new Teacher();
        ResponseStatus rs = new ResponseStatus();
        StringBuilder message = new StringBuilder();

        if (addTeacherDTO != null) {
            if (userRepo.findByUsername(addTeacherDTO.getUser_name()) != null) {
                message.append("Username ");
            }
            if (userRepo.findByEmail(addTeacherDTO.getEmail()) != null) {
                message.append("Email ");
            }
            if (!StringUtils.isBlank(message)) {
                message.append("is existed");
                rs.setMessage(message.toString());
                rs.setState(false);
                return rs;
            }
            try {
                if (addTeacherDTO.getUser_name().equals(userRepo.findById(addTeacherDTO.getId()).get().getUsername())) {
                    if (addTeacherDTO.getEmail().equals(userRepo.findById(addTeacherDTO.getId()).get().getEmail())) {
                        user.setId(addTeacherDTO.getId());
                        user.setUsername(userRepo.findById(addTeacherDTO.getId()).get().getUsername());
                        user.setFullname(addTeacherDTO.getFull_name());
                        user.setPassword(userRepo.findById(addTeacherDTO.getId()).get().getPassword());
                        user.setEmail(userRepo.findById(addTeacherDTO.getId()).get().getEmail());
                        user.setPhone(addTeacherDTO.getPhone());
                        user.setAddress(addTeacherDTO.getAddress());
                        user.setActive(addTeacherDTO.isActive());
                        userRepo.save(user);
                        rs.setMessage("Ok");
                        rs.setState(true);
                    }
                }

                if (userRepo.findByUsername(addTeacherDTO.getUser_name()) == null) {
                    if (userRepo.findByEmail(addTeacherDTO.getEmail()) == null) {
                        user.setId(addTeacherDTO.getId());
                        user.setUsername(userRepo.findById(addTeacherDTO.getId()).get().getUsername());
                        user.setFullname(addTeacherDTO.getFull_name());
                        user.setPassword(userRepo.findById(addTeacherDTO.getId()).get().getPassword());
                        user.setEmail(userRepo.findById(addTeacherDTO.getId()).get().getEmail());
                        user.setPhone(addTeacherDTO.getPhone());
                        user.setAddress(addTeacherDTO.getAddress());
                        user.setActive(addTeacherDTO.isActive());
                        userRepo.save(user);
                        rs.setMessage("Ok");
                        rs.setState(true);
                    }
                } else {
                    rs.setMessage("Failure");
                    rs.setState(false);
                }
            } catch (Exception ex) {
                rs.setMessage("Failure");
                rs.setState(false);
            }
        }
        return rs;
    }

    @Override
    public ResponseStatus createAct(Activity activity) {
        ResponseStatus responseStatus = new ResponseStatus();
        try {
            String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(timeStamp, formatter);
            activity.setCreatedAt(localDateTime);
            activity.setUpdatedAt(localDateTime);
            activityRepo.save(activity);
            responseStatus.setMessage("Ok");
            responseStatus.setState(true);
            return responseStatus;
        } catch (Exception e) {
            responseStatus.setMessage("Failure");
            responseStatus.setState(false);
            return responseStatus;
        }
    }

    @Override
    public ResponseStatus deleteTeacher(Long id) {
        ResponseStatus responseStatus = new ResponseStatus();
        try {
            if (id != null) {
                userRepo.deactive(teacherRepo.findByUserId(id).getUserId());
                responseStatus.setState(true);
                responseStatus.setMessage("Success");
            } else {
                responseStatus.setState(false);
                responseStatus.setMessage("Failure");
            }
            return responseStatus;
        } catch (Exception e) {
            responseStatus.setState(false);
            responseStatus.setMessage("Failure");
            return responseStatus;
        }
    }

    @Override
    public List<TeacherDTO> list() {
        return teacherCustomRepo.getAllTeacher();
    }

    @Override
    public List<Classes> getClasses(String username) {
        try {
            User user = userRepo.findUserByUsername(username);
            return classRepo.findAllByTeacherId(teacherRepo.findByUserId(user.getId()).getId());
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public TimeTableDTO getTimetableByClasses(Long id) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        ArrayList<Map<LocalDate, String>> list1 = new ArrayList<Map<LocalDate, String>>();
        Map<LocalDate, String> map = new LinkedHashMap<>();
        List<SearchTimeTable> list = new ArrayList<>();
        TimeTableDTO timeTableDTO = new TimeTableDTO();
        try {
            SearchTimeTable searchTimeTable = timeTableCustomRepo.doSearch(id);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
            for (int i = 1; i <= 4; i++) {
                if (i == searchTimeTable.getSlot_id()) {
                    try {
                        String sDate = searchTimeTable.getStart_date();
                        Date sParseDate = sdf.parse(sDate);
                        searchTimeTable.setStart_date(sdf1.format(sParseDate));
                        String eDate = searchTimeTable.getEnd_date();
                        Date eParseDate = sdf.parse(eDate);
                        searchTimeTable.setEnd_date(sdf1.format(eParseDate));
                        list.add(searchTimeTable);
                    } catch (Exception e) {

                    }
                } else {
                    list.add(new SearchTimeTable());
                }
            }

//            c.setTime(new Date(searchTimeTable.getStart_date()));
//            c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
//            LocalDate ld = LocalDateTime.ofInstant(c.toInstant(), c.getTimeZone().toZoneId()).toLocalDate();
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate localDate = null, lastDate = null;
//
//            for (int j = 0; j < searchTimeTable.getNumber_slot() / 2; j++) {
//                if (j == 0) {
//                    localDate = LocalDate.parse(ld.toString(), dtf);
//                    lastDate = localDate.plus(6, ChronoUnit.DAYS);
//                    map.put(localDate, lastDate.toString());
//                } else if (j >= 1) {
//                    localDate = localDate.plus(7, ChronoUnit.DAYS);
//                    lastDate = localDate.plus(6, ChronoUnit.DAYS);
//                    map.put(localDate, lastDate.toString());
//                }
//            }
            list1.add(map);
            timeTableDTO.setTime_table(list);
//            timeTableDTO.setFor_time(list1);
            return timeTableDTO;
        } catch (Exception ex) {
            return null;
        }
    }

}
