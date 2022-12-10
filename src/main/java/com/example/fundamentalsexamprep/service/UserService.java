package com.example.fundamentalsexamprep.service;

import com.example.fundamentalsexamprep.model.dto.LoginDTO;
import com.example.fundamentalsexamprep.model.dto.RegisterDTO;

public interface UserService {
    boolean registerUser(RegisterDTO registerDTO);

    boolean loginUser(LoginDTO loginDTO);
}
