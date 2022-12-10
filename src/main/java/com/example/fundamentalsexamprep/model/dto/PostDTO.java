package com.example.fundamentalsexamprep.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostDTO {
    private Long id;
    private String mood;
    private int likes;
    private String content;
    private String author;
}
