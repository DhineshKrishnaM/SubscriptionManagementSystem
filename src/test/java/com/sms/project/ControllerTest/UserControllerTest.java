package com.sms.project.ControllerTest;

import com.sms.project.exceptions.UserException;
import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.user.controller.UserController;
import com.sms.project.user.entity.User;
import com.sms.project.user.entity.UserDto;
import com.sms.project.user.repo.UserRepo;
import com.sms.project.user.service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
 class UserControllerTest {
    User user=new User();
    UserDto userDto=new UserDto();
    Optional<User> listPlans = Optional.of(new User());
    List<User> listOfUser=new ArrayList<>();
    List<UserDto> listOfUserDto= Arrays.asList(userDto,userDto);
    @Mock
    UserServiceImpl userService;
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private UserController userController;
    @Mock
    private ModelMapper modelMapper;
    @BeforeEach
    void setup(){
        user=User.builder()
                .username("Melvin")
                .email("melvin@gmail.com")
                .password("jone@123")
                .build();
        userDto=UserDto.builder()
                .username("Melvin")
                .email("melvin@gmail.com")
                .password("jone@123")
                .build();
    }
    @Test
    void saveUser(){
        Mockito.when(userService.saveUser(userDto)).thenReturn(userDto);
        Assertions.assertThat(userController.createUser(userDto)).isEqualTo(userDto);
    }

    @Test
    void updateUser(){
     Mockito.when(userService.updateUserDetails(1,userDto)).thenReturn(userDto);
     Assertions.assertThat(userController.updateUserDetails(1,userDto)).isEqualTo(userDto);
    }
    @Test
    void deleteUser() throws UserException {
        Mockito.when(userService.deleteUserById(1)).thenReturn("User Details deleted");
        Assertions.assertThat(userController.deleteUserById(1).getStatusCodeValue()).isEqualTo(200);
    }
    @Test
    void getAllUsers(){
        Mockito.when(userService.getAllUsersList()).thenReturn(listOfUserDto);
        Assertions.assertThat(userController.getUserList()).isNotEmpty();
    }

}
