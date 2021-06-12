package com.example.rest.service;

import com.example.rest.entity.UserEntity;
import com.example.rest.exception.UserAlreadyExistException;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.model.User;
import com.example.rest.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return (UserDetails) user;
    }

    public UserEntity store(UserEntity user) throws UserAlreadyExistException {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException("Пользователь с таким именим уже существует");
        }

        return userRepo.save(user);
    }

    public User show(Long id) throws UserNotFoundException {
        UserEntity user = userRepo.findById(id).get();

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return User.toModel(user);
    }

    public Long delete(Long id) {
        userRepo.deleteById(id);

        return id;
    }
}
