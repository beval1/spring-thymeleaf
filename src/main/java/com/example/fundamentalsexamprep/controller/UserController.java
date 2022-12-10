package com.example.fundamentalsexamprep.controller;

import com.example.fundamentalsexamprep.model.dto.LoginDTO;
import com.example.fundamentalsexamprep.model.dto.RegisterDTO;
import com.example.fundamentalsexamprep.security.CurrentUser;
import com.example.fundamentalsexamprep.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CurrentUser currentUser;

    public UserController(UserService userService, CurrentUser currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @GetMapping("/register")
    public String register(){
        if (currentUser.getId() != null){
            return "redirect:/home";
        }

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid RegisterDTO registerDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        if (currentUser.getId() != null){
            return "redirect:/home";
        }

        if (bindingResult.hasErrors() || !userService.registerUser(registerDTO)){
            redirectAttributes.addFlashAttribute("registerDTO", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);

            return "redirect:register";
        }

        return "redirect:login";
    }

    @GetMapping("/login")
    public String login(){
        if (currentUser.getId() != null){
            return "redirect:/home";
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginDTO loginDTO, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes){

        if (currentUser.getId() != null){
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginDTO",
                    bindingResult);

            return "redirect:login";
        }

        if(!userService.loginUser(loginDTO)){
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute("badCredentials", true);

            return "redirect:login";
        }

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        if (currentUser.getId() == null){
            return "redirect:/home";
        }

        httpSession.invalidate();
        return "index";
    }

    @ModelAttribute("loginDTO")
    public LoginDTO loginDTO(){
        return new LoginDTO();
    }

    @ModelAttribute("badCredentials")
    public boolean badCredentials(){
        return false;
    }

    @ModelAttribute("registerDTO")
    public RegisterDTO registerDTO(){
        return new RegisterDTO();
    }
}
