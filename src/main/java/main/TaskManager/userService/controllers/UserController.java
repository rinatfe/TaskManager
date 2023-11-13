package main.TaskManager.userService.controllers;

import jakarta.servlet.http.HttpSession;
import main.TaskManager.userService.entity.User;
import main.TaskManager.userService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Проверка существует ли юзер
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            // Юзернейм уже используется, return conflict status
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        // Проверка возможно емаил уже существует
        Optional<User> existingEmail = userService.findByEmail(user.getEmail());
        if (existingEmail.isPresent()) {
            // Email уже существует, return conflict status
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Сохранить пользователля и вернуть сохраненный объект
        User savedUser = userService.createUser(user.getUsername(), user.getPassword(), user.getEmail());

        // Return the saved User object as the response, along with HTTP status 201 (Created)
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> login(@RequestBody User login, HttpSession session) {
        Optional<User> optionalUser = userService.findByUsername(login.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                // The password matches, return the user and OK status
                session.setAttribute("user", user);
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/api/main/all").build();
            }
        }
        // Username not found or password mismatch, return Unauthorized error
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> userOptional = userService.findByUsername(username);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
