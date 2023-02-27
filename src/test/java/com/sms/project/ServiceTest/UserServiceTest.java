package com.sms.project.ServiceTest;

import com.sms.project.exceptions.UserException;
import com.sms.project.subcription.entity.Subscription;
import com.sms.project.user.entity.User;
import com.sms.project.user.entity.UserDto;
import com.sms.project.user.repo.UserRepo;
import com.sms.project.user.service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepo userRepo;
    @Mock
    ModelMapper modelMapper;
    @Mock
    private User user;
    @Mock
    private User user1;
    @Mock
    List<User> list = Arrays.asList(user, user1);
    @Mock
    private UserDto userDto;
    @Mock
    private UserDto userDto1;
    @Mock
    List<UserDto> userDtos = Arrays.asList(userDto, userDto1);
    @Mock
    private Subscription subscription;
    @Mock
    List<Subscription> sub=Arrays.asList(subscription);

    @BeforeEach
    void setup() {
        user1.setId(1);
        user1.setDeleted_at(null);
        user1.setEmail("mani@gmail.com");
        user1.setPassword("swd@123");
        user1.setRole("role_b2b");

        userDto1.setId(1);
        userDto1.setEmail("mani@gmail.com");
        userDto1.setPassword("swd@123");
        userDto1.setRole("role_b2b");

        userDto = UserDto.builder()
                .id(4)
                .username("velu")
                .email("vel@gmai.com")
                .password("Admin@123")
                .role("ROLE_B2B")
                .build();
        user = User.builder()
                .username("velu")
                .email("vel@gmai.com")
                .password("Admin@123")
                .role("ROLE_B2B")
                .subscriptions(sub)
                .build();
    }

    @BeforeEach
    void add() {
        list.add(user);
        list.add(user1);
        userDtos.add(userDto);
        userDtos.add(userDto);
    }

    @Test
    void getAllUser() {
        Mockito.when(userRepo.findAll()).thenReturn(list);
        Assertions.assertThat(userService.getAllUsersList()).isNotNull();
    }

    @Test
    void deleteUser() throws UserException {
        Mockito.when(userRepo.findById(1)).thenReturn(Optional.ofNullable(user));
        Mockito.when(userRepo.saveAndFlush(user)).thenReturn(user);
        Assertions.assertThat(userService.deleteUserById(1)).isEqualTo("User Details deleted");
    }
    @Test
    void updateUser(){
        Mockito.when(userRepo.findById(1)).thenReturn(Optional.ofNullable(user));
        Mockito.when(userRepo.save(user)).thenReturn(user);
        Mockito.when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);
        Assertions.assertThat(userService.updateUserDetails(1,userDto)).isEqualTo(userDto);
    }
    @Test
    void getUserById() throws UserException {
        Mockito.when(userRepo.findById(4)).thenReturn(Optional.ofNullable(user));
        Mockito.when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);
        Assertions.assertThat(userService.getUserById(4)).isEqualTo(userDto);
    }
}
