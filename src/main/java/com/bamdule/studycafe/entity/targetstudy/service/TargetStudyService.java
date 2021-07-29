package com.bamdule.studycafe.entity.targetstudy.service;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

public interface TargetStudyService {

    public void saveTargetStudy(Integer memberId, LocalDate studyDate, Integer targetStudyHour);

}
