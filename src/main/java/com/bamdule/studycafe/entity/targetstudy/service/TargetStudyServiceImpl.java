package com.bamdule.studycafe.entity.targetstudy.service;

import com.bamdule.studycafe.entity.targetstudy.TargetStudy;
import com.bamdule.studycafe.entity.targetstudy.repository.TargetStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class TargetStudyServiceImpl implements TargetStudyService {

    @Autowired
    private TargetStudyRepository targetStudyRepository;


    @Override
    public void saveTargetStudy(Integer memberId, LocalDate studyDate, Integer targetStudyHour) {

        Optional<TargetStudy> optionalTargetStudy = targetStudyRepository.getTargetStudyByMemberIdAndDate(memberId, studyDate);

        TargetStudy targetStudy = null;

        if (optionalTargetStudy.isPresent()) {
            targetStudy = optionalTargetStudy.get();
            targetStudy.setTargetStudyHour(targetStudyHour);
        } else {
            targetStudy = TargetStudy.builder()
                    .memberId(memberId)
                    .date(studyDate)
                    .targetStudyHour(targetStudyHour)
                    .build();
        }

        targetStudyRepository.save(targetStudy);

    }
}
