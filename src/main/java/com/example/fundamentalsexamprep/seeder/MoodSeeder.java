package com.example.fundamentalsexamprep.seeder;

import com.example.fundamentalsexamprep.model.entity.MoodEntity;
import com.example.fundamentalsexamprep.model.enums.MoodEnum;
import com.example.fundamentalsexamprep.repository.MoodRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MoodSeeder implements ApplicationRunner {

    private final MoodRepository moodRepository;

    public MoodSeeder(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (moodRepository.count() == 0) {
            moodRepository.save(MoodEntity.builder().name(MoodEnum.HAPPY).build());
            moodRepository.save(MoodEntity.builder().name(MoodEnum.SAD).build());
            moodRepository.save(MoodEntity.builder().name(MoodEnum.INSPIRED).build());
        }
    }
}
