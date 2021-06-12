package com.example.rest.service;

import com.example.rest.entity.UserEntity;
import com.example.rest.exception.UserAlreadyExistException;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UserEntity registration(UserEntity user) throws UserAlreadyExistException {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException("Пользователь с таким именим уже существует");
        }

        return userRepo.save(user);
    }

    public UserEntity show(Long id) throws UserNotFoundException {
        UserEntity user = userRepo.findById(id).get();

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return user;
    }
}
