package com.sms.project.user.service;

import com.sms.project.exceptions.UserException;
import com.sms.project.user.entity.User;
import com.sms.project.user.entity.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserDto> getAllUsersList();

    String deleteUserById(int id) throws UserException;

    UserDto saveUser(UserDto user);

    UserDto updateUserDetails(int userId, UserDto userDto);

    UserDto getUserById(int userId) throws UserException;
}
