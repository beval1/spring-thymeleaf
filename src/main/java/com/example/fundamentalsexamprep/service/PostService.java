package com.example.fundamentalsexamprep.service;

import com.example.fundamentalsexamprep.model.dto.AddPostDTO;
import com.example.fundamentalsexamprep.model.dto.PostDTO;

import java.util.List;

public interface PostService {
    boolean addPost(AddPostDTO addPostDTO);

    List<PostDTO> getOwnPosts(Long id);
    List<PostDTO> getOtherPosts(Long id);

    void removePost(Long postId);
    void likePost(Long postId);
}
