package com.example.fundamentalsexamprep.service.impl;

import com.example.fundamentalsexamprep.model.dto.LoginDTO;
import com.example.fundamentalsexamprep.model.dto.RegisterDTO;
import com.example.fundamentalsexamprep.model.entity.UserEntity;
import com.example.fundamentalsexamprep.repository.UserRepository;
import com.example.fundamentalsexamprep.security.CurrentUser;
import com.example.fundamentalsexamprep.service.UserService;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    @Override
    public boolean registerUser(RegisterDTO registerDTO) {
        //check password match
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            return false;
        }
        //check unique email
        Optional<UserEntity> byEmail = this.userRepository.findByEmail(registerDTO.getEmail());
        if(byEmail.isPresent()){
            return false;
        }
        //check unique username
        Optional<UserEntity> byUsername = this.userRepository.findByUsername(registerDTO.getUsername());
        if(byUsername.isPresent()){
            return false;
        }

        UserEntity userEntity = modelMapper.map(registerDTO, UserEntity.class);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public boolean loginUser(LoginDTO loginDTO) {
        UserEntity userEntity = userRepository.findByUsername(loginDTO.getUsername())
                .orElse(null);

        if (userEntity == null) {
            return false;
        }

        if (!loginDTO.getPassword().equals(userEntity.getPassword())){
            return false;
        }

        currentUser.setId(userEntity.getId());
        currentUser.setUsername(userEntity.getUsername());
        return true;
    }
}
