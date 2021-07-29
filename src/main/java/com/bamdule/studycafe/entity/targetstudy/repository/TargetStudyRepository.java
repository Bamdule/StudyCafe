package com.bamdule.studycafe.entity.targetstudy.repository;

import com.bamdule.studycafe.entity.targetstudy.TargetStudy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface TargetStudyRepository extends JpaRepository<TargetStudy, Integer> {
    public Optional<TargetStudy> getTargetStudyByMemberIdAndDate(Integer memberId, LocalDate date);
}
