package com.fastcampus.programming.dmaker.repository;

import com.fastcampus.programming.dmaker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// entity 개체를 영속화 하기위해사용
@Repository
public interface DeveloperRepository
        extends JpaRepository<Developer,Long> {

}
