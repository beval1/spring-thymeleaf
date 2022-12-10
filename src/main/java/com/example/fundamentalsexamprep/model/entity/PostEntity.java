package com.example.fundamentalsexamprep.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class PostEntity extends BaseEntity{
    @Column(nullable = false)
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mood_id")
    private MoodEntity moodEntity;

    @ManyToMany(mappedBy = "likedPosts")
    private Set<UserEntity> likes = new LinkedHashSet<>();

//    @ManyToMany
//    @JoinTable(name = "posts_likes",
//            joinColumns = @JoinColumn(name = "post_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private Set<UserEntity> likes = new LinkedHashSet<>();

}
