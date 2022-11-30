package com.example.JWTSecure.service.impl;

import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.domain.Curriculum;
import com.example.JWTSecure.domain.Student;
import com.example.JWTSecure.domain.StudentInClass;
import com.example.JWTSecure.domain.User;
import com.example.JWTSecure.repo.CurriculumRepo;
import com.example.JWTSecure.repo.StudentInClassRepo;
import com.example.JWTSecure.repo.StudentRepo;
import com.example.JWTSecure.repo.UserRepo;
import com.example.JWTSecure.repo.impl.StudentCustomRepo;
import com.example.JWTSecure.service.StudentService;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final PasswordEncoder passwordEncoder;
    private final StudentRepo studentRepo;
    private final UserRepo userRepo;
    private final StudentCustomRepo studentCustomRepo;
    private final CurriculumRepo curriculumRepo;
    private final StudentInClassRepo studentInClassRepo;


    @Override
    public StudentDTO getStudent(StudentDTO studentDTO) {
        try {
            return studentCustomRepo.getStudent(studentDTO);
        } catch (Exception e) {
            return null;
        }
    }

    public List<StudentDTO> getNotDuplicate(List<StudentDTO> list) {
        List<StudentDTO> li = new ArrayList<>();
        Set<Long> set = new HashSet<>();
        for (StudentDTO i : list) {
            if (!set.contains(i.getStudent_Id())) {
                li.add(i);
                set.add(i.getStudent_Id());
            }
        }
        return li;
    }

    public List<String> groupClasses(List<StudentDTO> list, Long id) {
        List<String> listClasses = new ArrayList<>();
        for (StudentDTO i : list) {
            if (i.getStudent_Id() == id) {
                listClasses.add(i.getClass_name());
            }
        }
        return listClasses;
    }

    public List<String> groupCourses(List<StudentDTO> list, Long id) {
        List<String> listCourses = new ArrayList<>();
        for (StudentDTO i : list) {
            if (i.getStudent_Id() == id) {
                listCourses.add(i.getCourse_name());
            }
        }
        return listCourses;
    }

    @Override
    public SearchResultDTO<StudentDTO> getAllStudent(StudentDTO studentDTO) {
        List<StudentDTO> dataResult;
        SearchResultDTO<StudentDTO> searchResult = new SearchResultDTO<>();

        try {
            Integer totalRecord = studentCustomRepo.getTotal(studentDTO).size();
            dataResult = studentCustomRepo.doSearch(studentDTO);

            for (StudentDTO i : dataResult) {
                if (groupClasses(dataResult, i.getStudent_Id()) != null) {
                    i.setClasses(groupClasses(dataResult, i.getStudent_Id()));
                    i.setCourses(groupCourses(dataResult, i.getStudent_Id()));
                }
            }

            if (dataResult != null && !dataResult.isEmpty()) {
                searchResult.setCode("0");
                searchResult.setSuccess(true);
                searchResult.setTitle("Success");
                searchResult.setMessage("Success");
                searchResult.setResultData(getNotDuplicate(dataResult));
                searchResult.setTotalRecordNoLimit(getNotDuplicate(dataResult).size());
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
    public SearchResultDTO<StudentDTO> getStudentPending(StudentDTO studentDTO) {
        List<StudentDTO> dataResult;
        SearchResultDTO<StudentDTO> searchResult = new SearchResultDTO<>();

        try {
            Integer totalRecord = studentCustomRepo.getTotalPending(studentDTO).size();
            dataResult = studentCustomRepo.doSearchPending(studentDTO);
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
    public ResponseStatus addStudent(AddStudentDTO addStudentDTO) {
        User user = new User();
        Student student = new Student();
        ResponseStatus rs = new ResponseStatus();
        StringBuilder message = new StringBuilder();

        if (addStudentDTO != null) {
            if (userRepo.findByUsername(addStudentDTO.getUsername()) != null) {
                message.append("Username ");
            }
            if (userRepo.findByEmail(addStudentDTO.getEmail()) != null) {
                message.append("Email ");
            }
            if (!StringUtils.isBlank(message)) {
                message.append("is existed");
                rs.setMessage(message.toString());
                rs.setState(false);
                return rs;
            }
            try {
                if (userRepo.findByUsername(addStudentDTO.getUsername()) == null) {
                    if (userRepo.findByEmail(addStudentDTO.getEmail()) == null) {
                        if (userRepo.findByPhone(addStudentDTO.getPhone()) == null) {
                            user.setUsername(addStudentDTO.getUsername());
                            user.setFullname(addStudentDTO.getFullname());
                            user.setPassword(passwordEncoder.encode(addStudentDTO.getPassword()));
                            user.setEmail(addStudentDTO.getEmail());
                            user.setPhone(addStudentDTO.getPhone());
                            user.setAddress(addStudentDTO.getAddress());
                            user.setActive(addStudentDTO.isActive());
                            userRepo.save(user);

                            student.setUserId(userRepo.findTopByOrderByIdDesc().getId());
                            student.setRoleId(4L);
                            studentRepo.save(student);
                            rs.setMessage("Ok");
                            rs.setState(true);
                        }
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
    public ResponseStatus editStudent(AddStudentDTO addStudentDTO) {
        User user = new User();
        Student student = new Student();
        ResponseStatus rs = new ResponseStatus();
        StringBuilder message = new StringBuilder();

        if (addStudentDTO != null) {
            if (userRepo.findByUsername(addStudentDTO.getUsername()) != null) {
                message.append("Username ");
            }
            if (userRepo.findByEmail(addStudentDTO.getEmail()) != null) {
                message.append("Email ");
            }
            if (!StringUtils.isBlank(message)) {
                message.append("is existed");
                rs.setMessage(message.toString());
                rs.setState(false);
                return rs;
            }

            try {
                if (userRepo.findByUsername(addStudentDTO.getUsername()) == null) {
                    if (userRepo.findByEmail(addStudentDTO.getEmail()) == null) {
                        student.setUserId(addStudentDTO.getId());
                        student.setRoleId(4L);
                        studentRepo.save(student);

                        user.setId(addStudentDTO.getId());
                        user.setUsername(addStudentDTO.getUsername());
                        user.setFullname(addStudentDTO.getFullname());
                        user.setPassword(userRepo.findById(addStudentDTO.getId()).get().getPassword());
                        user.setEmail(addStudentDTO.getEmail());
                        user.setPhone(addStudentDTO.getPhone());
                        user.setAddress(addStudentDTO.getAddress());
                        user.setActive(addStudentDTO.isActive());
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
    public List<StudentDTO> getListStudentByIdClass(Long id) {
        try {
            return studentCustomRepo.getListStudentByIdClass(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<StudentDTO> detailStudentClass(Long id) {
        try {
            return studentCustomRepo.detailStudentClass(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseStatus updatePending(Long id) {
        ResponseStatus responseStatus = new ResponseStatus();
        try {
            if (id != null) {
                studentRepo.updatePending(id);
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
    public ResponseStatus deletePending(Long id) {
        return null;
    }

    @Override
    public ResponseStatus addCurriculum(Curriculum curriculum) {
        ResponseStatus responseStatus = new ResponseStatus();
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(timeStamp, formatter);
        try {
            curriculum.setCreatedAt(localDateTime);
            curriculum.setUpdatedAt(localDateTime);
            curriculumRepo.save(curriculum);
            responseStatus.setState(true);
            responseStatus.setMessage("Success");
        } catch (Exception ex) {
            responseStatus.setState(false);
            responseStatus.setMessage("Failure");
        }
        return responseStatus;
    }

    @Override
    public ResponseStatus registerCourse(RegisterClass registerClass) {
        ResponseStatus responseStatus = new ResponseStatus();
        StudentInClass studentInClass = new StudentInClass();
        Long userId = userRepo.findByUsername(registerClass.getUsername()).getId();
        Long studentId = studentRepo.findByUserId(userId).getId();
        try {
            if (studentInClassRepo.countStudentInClassByClassId(registerClass.getClassId()) <= 30) {
                if (studentInClassRepo.findStudentInClassByClassIdAndStudentId(registerClass.getClassId(), studentId) == null) {
                    studentInClass.setIsPaid(false);
                    studentInClass.setClassId(registerClass.getClassId());
                    studentInClass.setStudentId(studentId);
                    studentInClassRepo.save(studentInClass);
                    responseStatus.setMessage("Success");
                    responseStatus.setState(true);
                } else {
                    responseStatus.setMessage("Failure");
                    responseStatus.setState(false);
                }
            } else {
                responseStatus.setMessage("Failure");
                responseStatus.setState(false);
            }
        } catch (Exception ex) {
            responseStatus.setMessage("Failure");
            responseStatus.setState(false);
        }
        return responseStatus;
    }

}

