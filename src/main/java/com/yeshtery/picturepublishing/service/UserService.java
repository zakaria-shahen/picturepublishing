package com.yeshtery.picturepublishing.service;

import com.yeshtery.picturepublishing.enums.Authority;
import com.yeshtery.picturepublishing.model.Users;
import com.yeshtery.picturepublishing.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Users> findById(Long id){
        return userRepository.findById(id);
    }


    public Users saveUser(Users user){
        user.setId(null);
        user.setAuthority(Authority.USER);
        return save(user);    }

    public Users saveAdmin(Users user){
        user.setId(null);
        user.setAuthority(Authority.ADMIN);
        return save(user);
    }

    private Users save(Users user){
        return userRepository.save(user);
    }


    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
