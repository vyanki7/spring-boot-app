package com.mysql.springboot.mysqlspringboot.controllers;
import com.mysql.springboot.mysqlspringboot.entity.User;
import com.mysql.springboot.mysqlspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping("createUser")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User created successfully.");
        response.put("user", savedUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get all users
    @GetMapping("getUsers")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Users retrieved successfully.");
        response.put("users", users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get user by ID
    @GetMapping("/getUser/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        Map<String, Object> response = new HashMap<>();
        if (user.isPresent()) {
            response.put("message", "User found.");
            response.put("user", user.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "User not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Update a user by ID
    @PutMapping("/editUser/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        Map<String, Object> response = new HashMap<>();
        if (updatedUser != null) {
            response.put("message", "User updated successfully.");
            response.put("user", updatedUser);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "User not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a user by ID
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        Map<String, String> response = new HashMap<>();
        if (deleted) {
            response.put("message", "User deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response.put("message", "User not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
