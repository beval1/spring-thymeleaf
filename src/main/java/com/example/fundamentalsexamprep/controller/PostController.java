package com.example.fundamentalsexamprep.controller;

import com.example.fundamentalsexamprep.model.dto.AddPostDTO;
import com.example.fundamentalsexamprep.security.CurrentUser;
import com.example.fundamentalsexamprep.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CurrentUser currentUser;

    public PostController(PostService postService, CurrentUser currentUser) {
        this.postService = postService;
        this.currentUser = currentUser;
    }

    @GetMapping("/add")
    public String getAdd(){
        if (currentUser.getId() == null){
            return "redirect:/";
        }

        return "post-add";
    }

    @GetMapping("/remove/{postId}")
    public String removePost(@PathVariable("postId") Long postId){
        postService.removePost(postId);
        return "redirect:/home";
    }

    @GetMapping("/like/{postId}")
    public String likePost(@PathVariable("postId") Long postId){
        postService.likePost(postId);
        return "redirect:/home";
    }

    @PostMapping("/add")
    public String add(@Valid AddPostDTO addPostDTO, BindingResult bindingResult,
                      RedirectAttributes redirectAttributes){

        if (currentUser.getId() == null){
            return "redirect:/";
        }

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addPostDTO", addPostDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addPostDTO", bindingResult);

            return "redirect:add";
        }

        postService.addPost(addPostDTO);
        return "redirect:/home";
    }

    @ModelAttribute("addPostDTO")
    public AddPostDTO addPostDTO(){
        return new AddPostDTO();
    }

}
