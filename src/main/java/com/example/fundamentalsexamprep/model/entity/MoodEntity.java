package com.example.fundamentalsexamprep.model.entity;

import com.example.fundamentalsexamprep.model.enums.MoodEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class MoodEntity extends BaseEntity {
    @Enumerated
    @Column(unique = true, nullable = false)
    private MoodEnum name;

    private String description;

}