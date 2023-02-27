package com.sms.project.RepositoryTest;

import com.sms.project.SubscriptionManagementSystemApplication;
import com.sms.project.common.Entity.CommonEntity;
import com.sms.project.user.entity.User;
import com.sms.project.user.repo.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = SubscriptionManagementSystemApplication.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepo userRepo;
    CommonEntity commonEntity=new CommonEntity();
    User user=new User();
    @BeforeEach
    void setup(){
        user=User.builder()
                .username("Melvin")
                .email("Mell@gmail.com")
                .password("jone@123")
                .role("Role_User")
                .build();
    }
    @DisplayName("To create a new user")
    @Test
    void saveUser(){
        userRepo.save(user);
        Assertions.assertThat(user.getId()).isPositive();
    }
    @DisplayName("Get user details by Id.")
    @Test
    void getById(){
        user=userRepo.findById(1).get();
        Assertions.assertThat(user.getId()).isEqualTo(1);
    }
    @DisplayName("To check list of users is not empty")
    @Test
    void getAllUsers(){
        List<User> user1 = userRepo.findAll();
        Assertions.assertThat(user1).isNotEmpty();
    }
    @DisplayName("To update user details by using Id.")
    @Test
    void updateUserId(){
        user=userRepo.findById(1).get();
        user.setEmail("ani@gmail.com");
        User user1 = userRepo.save(user);
        Assertions.assertThat(user.getId()).isEqualTo(user1.getId());
    }
}
