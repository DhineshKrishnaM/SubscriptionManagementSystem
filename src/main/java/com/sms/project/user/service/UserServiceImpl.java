package com.sms.project.user.service;

import com.sms.project.exceptions.UserException;
import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.plandetails.entity.PlanDto;
import com.sms.project.product.service.ProductService;
import com.sms.project.subcription.entity.Subscription;
import com.sms.project.subcription.entity.SubscriptionDto;
import com.sms.project.user.entity.User;
import com.sms.project.user.entity.UserDto;
import com.sms.project.user.repo.UserRepo;
import com.sms.project.utility.errorcode.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductService productService;

    @Override
    public List<UserDto> getAllUsersList() {

        List<User> userList = userRepo.findAll();
        return userList.stream().map(userValue -> {
                    List<Subscription> subscriptionList = getList(userValue);
                    userValue.setSubscriptions(subscriptionList);
                    UserDto userDto = modelMapper.map(userValue, UserDto.class);
                    userDto.setSubscriptions(subscriptionList.stream().map(subscription -> {
                        PlanDetail plan = getPlanDetails(subscription);
                        SubscriptionDto subscriptionDto = modelMapper.map(subscription, SubscriptionDto.class);
                        subscriptionDto.setPlanDto(modelMapper.map(plan, PlanDto.class));
                        return subscriptionDto;
                    }).collect(Collectors.toList()));
                    return userDto;
                }
        ).collect(Collectors.toList());
    }

    private PlanDetail getPlanDetails(Subscription subscription) {
        return subscription.getPlanDetail();
    }

    private List<Subscription> getList(User userValue) {
        return userValue.getSubscriptions();
    }

    @Override
    public String deleteUserById(int id) throws UserException {
        User getUser = userRepo.findById(id).orElseThrow(() -> new UserException("That user not available here or not found.."));
        getUser.setDeleted_at(LocalDate.now());
        userRepo.saveAndFlush(getUser);
        log.info("Deleted user");
        return "User Details deleted";
    }

    @Override
    public UserDto saveUser(UserDto user) {
        Boolean isValid = validateCredentials(user);
        if (!isValid) throw new IllegalArgumentException(ErrorCodes.EMAIL_INVALID);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException(ErrorCodes.EMAIL_ALREADY_REGISTERED);
        }
        User userDet = modelMapper.map(user, User.class);
        User userDt = userRepo.save(userDet);
        return modelMapper.map(userDt, UserDto.class);
    }

    @Override
    public UserDto updateUserDetails(int userId, UserDto userDto) {
        log.info("User Details updating....");
        User userProfile = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException(ErrorCodes.USER_NOT_FOUND));
        userProfile.setUsername(userDto.getUsername());
        userProfile.setEmail(userDto.getEmail());
        userRepo.save(userProfile);
        return modelMapper.map(userProfile, UserDto.class);
    }

    @Override
    public UserDto getUserById(int userId) throws UserException {
        log.info(userId + " ID is fetching details...");
        User userInfo = userRepo.findById(userId).orElseThrow(() -> new UserException("UserDetails Not Found"));
        List<Subscription> subscriptionList = userInfo.getSubscriptions();
        UserDto userDto = modelMapper.map(userInfo, UserDto.class);
        userDto.setSubscriptions(subscriptionList.stream().map(subscription -> {
            SubscriptionDto subscriptionDto = modelMapper.map(subscription, SubscriptionDto.class);
            subscriptionDto.setPlanDto(modelMapper.map(subscription.getPlanDetail(), PlanDto.class));
            return subscriptionDto;
        }).collect(Collectors.toList()));
        return userDto;
    }


    private Boolean validateCredentials(UserDto user) {
        String regexEmail = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        if (!Pattern.compile(regexEmail, Pattern.CASE_INSENSITIVE).matcher((user.getEmail())).find()) {
            throw new IllegalArgumentException(ErrorCodes.EMAIL_INVALID);
        }
        String password = user.getPassword();
        String regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (!Pattern.compile(regexp).matcher(password).find()) {
            throw new IllegalArgumentException(ErrorCodes.PASSWORD_INVALID + " Password should be minimum 8 character, " +
                    " It contains at least one uppercase, one lowercase and one number");
        }
        return true;
    }
}
