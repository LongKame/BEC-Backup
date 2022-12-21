package com.example.JWTSecure.service.impl;

import com.example.JWTSecure.domain.Classes;
import com.example.JWTSecure.repo.ClassRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CronJobService {

    private final ClassRepo classRepo;

    @SneakyThrows
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 12)
    public void enableClassExpire() {
        Date date = new Date();
        List<Classes> classes = classRepo.findAll();
        for (Classes i : classes) {
            if (date.after(i.getEndDate()) && i.isActive()) {
                classRepo.deActive(i.getId());
            }
        }
    }
}
