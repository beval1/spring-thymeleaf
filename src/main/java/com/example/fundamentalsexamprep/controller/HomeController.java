package com.example.fundamentalsexamprep.controller;

import com.example.fundamentalsexamprep.model.dto.PostDTO;
import com.example.fundamentalsexamprep.security.CurrentUser;
import com.example.fundamentalsexamprep.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final PostService postService;
    private final CurrentUser currentUser;

    public HomeController(PostService postService, CurrentUser currentUser) {
        this.postService = postService;
        this.currentUser = currentUser;
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (currentUser.getId() == null){
            return "redirect:/";
        }

        List<PostDTO> ownPosts = this.postService.getOwnPosts(currentUser.getId());
        List<PostDTO> otherPosts = this.postService.getOtherPosts(currentUser.getId());

        System.out.println(otherPosts);
        System.out.println(ownPosts);

        model.addAttribute("ownPosts", ownPosts);
        model.addAttribute("otherPosts", otherPosts);

        return "home";
    }

    @GetMapping("/")
    public String index() {
        if (currentUser.getId() != null){
            return "redirect:/home";
        }

        return "index";
    }
}
