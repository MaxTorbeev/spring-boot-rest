package com.example.rest.controller;

import com.example.rest.entity.UserEntity;
import com.example.rest.exception.UserAlreadyExistException;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity show(@PathVariable("id") long id) {
        try {
            return ResponseEntity.ok(userService.show(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PostMapping
    public ResponseEntity<String> registration(@RequestBody UserEntity user) {
        try {
            userService.registration(user);
            return ResponseEntity.ok("Пользователь успешно сохранен.");
        } catch (UserAlreadyExistException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
