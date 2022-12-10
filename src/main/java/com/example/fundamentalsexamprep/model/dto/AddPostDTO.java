package com.example.fundamentalsexamprep.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddPostDTO {
    @Size(min = 2, max = 150)
    @NotBlank
    private String content;
    @NotBlank
    private String mood;
}
