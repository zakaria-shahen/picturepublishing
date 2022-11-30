package com.yeshtery.picturepublishing.controller;

import com.yeshtery.picturepublishing.model.User;
import com.yeshtery.picturepublishing.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("registration")
    public Map<String, String> post(@RequestBody @Valid User user) {

        userService.saveUser(user);

        return Map.of("Message", "created");
    }

}
