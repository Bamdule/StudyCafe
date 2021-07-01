package com.bamdule.studycafe.studycafe.repository;

import com.bamdule.studycafe.studycafe.StudyCafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyCafeRepository extends JpaRepository<StudyCafe, Integer> , StudyCafeRepositoryCustom {
}
