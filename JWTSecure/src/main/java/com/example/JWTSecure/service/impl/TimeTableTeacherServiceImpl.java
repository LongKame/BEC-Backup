package com.example.JWTSecure.service.impl;
import com.example.JWTSecure.DTO.TimeTableStudentDTO;
import com.example.JWTSecure.DTO.TimeTableTeacherDTO;
import com.example.JWTSecure.DTO.Week;
import com.example.JWTSecure.repo.impl.ClassScheduleCustomRepo;
import com.example.JWTSecure.service.TimeTableTeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TimeTableTeacherServiceImpl implements TimeTableTeacherService {

    private final ClassScheduleCustomRepo classScheduleCustomRepo;

    @Override
    public List<TimeTableTeacherDTO> getTimeTableOfTeacher(TimeTableTeacherDTO timeTableTeacherDTO) {
        List<TimeTableTeacherDTO> list = classScheduleCustomRepo.getTimeTable(timeTableTeacherDTO);
        List<TimeTableTeacherDTO> list1 = new ArrayList<>();

        list1.add(0, null);
        list1.add(1, null);
        list1.add(2, null);
        list1.add(3, null);

        if (list == null || list.isEmpty()) {
            list1.set(0, new TimeTableTeacherDTO());
            list1.set(1, new TimeTableTeacherDTO());
            list1.set(2, new TimeTableTeacherDTO());
            list1.set(3, new TimeTableTeacherDTO());
            return list1;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list1.get(0) == null || list1.get(0).getClass_name() == null) {
                if (list.get(i).getSlot_of_date() == 1) {
                    list1.set(0, list.get(i));
                } else {
                    list1.set(0, new TimeTableTeacherDTO());
                }
            }
            if (list1.get(1) == null || list1.get(1).getClass_name() == null) {
                if (list.get(i).getSlot_of_date() == 2) {
                    list1.set(1, list.get(i));
                } else {
                    list1.set(1, new TimeTableTeacherDTO());
                }
            }
            if (list1.get(2) == null || list1.get(2).getClass_name() == null) {
                if (list.get(i).getSlot_of_date() == 3) {
                    list1.set(2, list.get(i));
                } else {
                    list1.set(2, new TimeTableTeacherDTO());
                }
            }
            if (list1.get(3) == null || list1.get(3).getClass_name() == null) {
                if (list.get(i).getSlot_of_date() == 4) {
                    list1.set(3, list.get(i));
                } else {
                    list1.set(3, new TimeTableTeacherDTO());
                }
            }
        }
        return list1;
    }

    @Override
    public List<Week> getEveryWeek() {
        Month[] month = {Month.JANUARY, Month.FEBRUARY, Month.MARCH,
                Month.APRIL, Month.MAY, Month.JUNE,
                Month.JULY, Month.AUGUST, Month.SEPTEMBER,
                Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER};

        List<Week> list = new ArrayList<>();
        LinkedHashSet<String> hashSet = new LinkedHashSet<String>();

        for (int i = 0; i < 12; i++) {
            YearMonth ym = YearMonth.of(2022, month[i]);
            LocalDate firstOfMonth = ym.atDay(1);
            TemporalAdjuster ta = TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY);
            LocalDate previousOrSameMonday = firstOfMonth.with(ta);
            LocalDate endOfMonth = ym.atEndOfMonth();
            LocalDate weekStart = previousOrSameMonday;
            do {
                LocalDate weekStop = weekStart.plusDays(6);
                if (weekStart.getYear() == 2022 && weekStop.getYear() == 2022) {
                    String dayOfWeekStart = String.valueOf(weekStart.getDayOfMonth());
                    String monthOfWeekStart = String.valueOf(weekStart.getMonthValue());
                    String dayOfWeekStop = String.valueOf(weekStop.getDayOfMonth());
                    String monthOfEndStart = String.valueOf(weekStop.getMonthValue());

                    if ((String.valueOf(weekStart.getDayOfMonth()).length() == 1)) {
                        dayOfWeekStart = "0" + String.valueOf(weekStart.getDayOfMonth());
                    }
                    if ((String.valueOf(weekStart.getMonthValue()).length() == 1)) {
                        monthOfWeekStart = "0" + String.valueOf(weekStart.getMonthValue());
                    }
                    if ((String.valueOf(weekStop.getDayOfMonth()).length() == 1)) {
                        dayOfWeekStop = "0" + String.valueOf(weekStop.getDayOfMonth());
                    }
                    if ((String.valueOf(weekStop.getMonthValue()).length() == 1)) {
                        monthOfEndStart = "0" + String.valueOf(weekStop.getMonthValue());
                    }
                    if (hashSet.add(dayOfWeekStart + "-" + monthOfWeekStart)) {
                        list.add(new Week(dayOfWeekStart + "-" + monthOfWeekStart, dayOfWeekStop + "-" + monthOfEndStart));
                    }
                }
                weekStart = weekStart.plusWeeks(1);
            } while (!weekStart.isAfter(endOfMonth));
        }
        return list;
    }

    @Override
    public List<TimeTableStudentDTO> getTimeTableOfStudent(TimeTableStudentDTO timeTableStudentDTO) {

        if(timeTableStudentDTO.getUser_name()!=null && timeTableStudentDTO.getDate_study()!=null){
            List<TimeTableStudentDTO> list = classScheduleCustomRepo.getTimeTableForStudent(timeTableStudentDTO);
            List<TimeTableStudentDTO> list1 = new ArrayList<>();

            list1.add(0, null);
            list1.add(1, null);
            list1.add(2, null);
            list1.add(3, null);

            if (list == null || list.isEmpty()) {
                list1.set(0, new TimeTableStudentDTO());
                list1.set(1, new TimeTableStudentDTO());
                list1.set(2, new TimeTableStudentDTO());
                list1.set(3, new TimeTableStudentDTO());
                return list1;
            }

            for (int i = 0; i < list.size(); i++) {
                if (list1.get(0) == null) {
                    if (list.get(i).getSlot_of_date() == 1) {
                        list1.set(0, list.get(i));
                    } else {
                        list1.set(0, new TimeTableStudentDTO());
                    }
                }
                if (list1.get(1) == null) {
                    if (list.get(i).getSlot_of_date() == 2) {
                        list1.set(1, list.get(i));
                    } else {
                        list1.set(1, new TimeTableStudentDTO());
                    }
                }
                if (list1.get(2) == null) {
                    if (list.get(i).getSlot_of_date() == 3) {
                        list1.set(2, list.get(i));
                    } else {
                        list1.set(2, new TimeTableStudentDTO());
                    }
                }
                if (list1.get(3) == null) {
                    if (list.get(i).getSlot_of_date() == 4) {
                        list1.set(3, list.get(i));
                    } else {
                        list1.set(3, new TimeTableStudentDTO());
                    }
                }
            }
            return list1;
        }
        return null;
    }
}
