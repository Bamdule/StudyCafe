package com.bamdule.studycafe.entity.studycafe.repository;

import com.bamdule.studycafe.entity.studycafe.StudyCafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyCafeRepository extends JpaRepository<StudyCafe, Integer> , StudyCafeRepositoryCustom {
}
