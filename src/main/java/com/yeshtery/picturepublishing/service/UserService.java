package com.yeshtery.picturepublishing.service;

import com.yeshtery.picturepublishing.enums.Authority;
import com.yeshtery.picturepublishing.model.User;
import com.yeshtery.picturepublishing.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }


    public User saveUser(User user){
        user.setId(null);
        user.setAuthority(Authority.USER);
        return save(user);    }

    public User saveAdmin(User user){
        user.setId(null);
        user.setAuthority(Authority.ADMIN);
        return save(user);
    }

    private User save(User user){
        return userRepository.save(user);
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
