package com.example.fundamentalsexamprep.repository;

import com.example.fundamentalsexamprep.model.entity.MoodEntity;
import com.example.fundamentalsexamprep.model.enums.MoodEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoodRepository extends JpaRepository<MoodEntity, Long> {
    Optional<MoodEntity> findByName(MoodEnum valueOf);
}
