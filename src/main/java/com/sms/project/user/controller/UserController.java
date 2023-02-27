package com.sms.project.user.controller;

import com.sms.project.exceptions.UserException;
import com.sms.project.subcription.service.SubscriptionImpl;
import com.sms.project.user.entity.Login;
import com.sms.project.user.entity.User;
import com.sms.project.user.entity.UserDto;
import com.sms.project.user.repo.UserRepo;
import com.sms.project.user.security.jwt.JwtUtils;
import com.sms.project.user.service.UserService;
import com.sms.project.utility.PDFService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    PDFService pdfService;
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private SubscriptionImpl subscription;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/v1/list")
    public List<UserDto> getUserList() {
        return userService.getAllUsersList();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/v1/delete")
    public ResponseEntity<String> deleteUserById(@RequestParam int id) throws UserException {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User details has been deleted", HttpStatus.OK);
    }

    @PostMapping("/v1/create")
    public UserDto createUser(@RequestBody UserDto user) {
        return userService.saveUser(user);
    }

    @PreAuthorize("hasAnyRole('ROLE_B2B', 'ROLE_ADMIN', 'ROLE_B2C')")
    @PutMapping("/v1/update")
    public UserDto updateUserDetails(@RequestParam int userId, @RequestBody UserDto userDto) {
        return userService.updateUserDetails(userId, userDto);
    }


    @PreAuthorize("hasAnyRole('ROLE_B2C', 'ROLE_B2B')")
    @GetMapping("/v1/getById")
    public UserDto getUserDetailsById(@RequestParam int userId) throws UserException {
        return userService.getUserById(userId);
    }

    @PostMapping("/v1/authenticate")
    public String generateToken(@RequestBody Login authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        return jwtUtils.generateJwtToken(authentication);
    }

    public User get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info(email);
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }
}
