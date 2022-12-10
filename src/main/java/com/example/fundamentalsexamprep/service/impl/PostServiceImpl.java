package com.example.fundamentalsexamprep.service.impl;

import com.example.fundamentalsexamprep.model.dto.AddPostDTO;
import com.example.fundamentalsexamprep.model.dto.PostDTO;
import com.example.fundamentalsexamprep.model.entity.MoodEntity;
import com.example.fundamentalsexamprep.model.entity.PostEntity;
import com.example.fundamentalsexamprep.model.entity.UserEntity;
import com.example.fundamentalsexamprep.model.enums.MoodEnum;
import com.example.fundamentalsexamprep.repository.MoodRepository;
import com.example.fundamentalsexamprep.repository.PostRepository;
import com.example.fundamentalsexamprep.repository.UserRepository;
import com.example.fundamentalsexamprep.security.CurrentUser;
import com.example.fundamentalsexamprep.service.PostService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;
    private final MoodRepository moodRepository;
    private final PostRepository postRepository;
    private final CurrentUser currentUser;

    public PostServiceImpl(UserRepository userRepository, MoodRepository moodRepository, PostRepository postRepository,
                           CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.moodRepository = moodRepository;
        this.postRepository = postRepository;
        this.currentUser = currentUser;
    }

    @Transactional
    @Override
    public boolean addPost(AddPostDTO addPostDTO) {
        UserEntity userEntity = userRepository.findById(currentUser.getId()).orElse(null);
        MoodEntity moodEntity = moodRepository.findByName(MoodEnum.valueOf(addPostDTO.getMood())).orElse(null);

        if (moodEntity == null || userEntity == null){
            return false;
        }

        postRepository.save(PostEntity.builder()
                .content(addPostDTO.getContent())
                .moodEntity(moodEntity)
                .likes(Set.of())
                .user(userEntity)
                .build());

        return true;
    }

    @Transactional
    @Override
    public List<PostDTO> getOwnPosts(Long id) {
        List<PostEntity> postEntities = postRepository.findAllByUserId(id);
        List<PostDTO> postDTOS = new ArrayList<>();
        postEntities.forEach(e -> postDTOS.add(mapToPostDTO(e)));
        return postDTOS;
    }

    @Transactional
    @Override
    public List<PostDTO> getOtherPosts(Long id) {
        List<PostEntity> postEntities = postRepository.findAllByUserIdNot(id);
        List<PostDTO> postDTOS = new ArrayList<>();
        postEntities.forEach(e -> postDTOS.add(mapToPostDTO(e)));
        return postDTOS;
    }

    @Transactional
    @Override
    public void removePost(Long postId) {
        //remove post only if you are the owner
        PostEntity postEntity = postRepository.findById(postId).orElse(null);
        UserEntity userEntity = userRepository.findById(currentUser.getId()).orElse(null);

        if(userEntity == null || postEntity == null || !postEntity.getUser().getId().equals(currentUser.getId())){
            return;
        }

        //first delete likes relation
        postEntity.getLikes().forEach(user -> user.getLikedPosts().remove(postEntity));
        postEntity.setLikes(Set.of());
        //finally delete post
        postRepository.deleteById(postId);
    }

    @Transactional
    @Override
    public void likePost(Long postId) {
        UserEntity userEntity = userRepository.findById(currentUser.getId()).orElse(null);
        PostEntity postEntity = postRepository.findById(postId).orElse(null);

        if(userEntity == null || postEntity == null){
            return;
        }

        //bidirectional relation
        postEntity.getLikes().add(userEntity);
        userEntity.getLikedPosts().add(postEntity);
    }

    private PostDTO mapToPostDTO(PostEntity e){
        return PostDTO
                .builder()
                .content(e.getContent())
                .id(e.getId())
                .mood(e.getMoodEntity().getName().name())
                .likes(e.getLikes().size())
                .author(e.getUser().getUsername())
                .build();
    }
}
