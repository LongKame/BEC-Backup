package com.example.JWTSecure.service;

import com.example.JWTSecure.DTO.ClassDTO;
import com.example.JWTSecure.DTO.ClassScheduleDTO;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.DTO.SearchResultDTO;
import java.text.ParseException;
import java.util.List;

public interface ClassService {
    SearchResultDTO<ClassDTO> getAllClass(ClassDTO classDTO);
    ResponseStatus addClass(ClassDTO classDTO) throws ParseException;
    ResponseStatus disableClass(Long id);
    List<ClassDTO> getTotalClass();
    List<ClassScheduleDTO> getClassById(Long class_id);
}
