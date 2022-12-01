package com.yeshtery.picturepublishing;

import com.yeshtery.picturepublishing.enums.FileStatus;
import com.yeshtery.picturepublishing.enums.ImageCategory;
import com.yeshtery.picturepublishing.model.Image;
import com.yeshtery.picturepublishing.model.Users;
import com.yeshtery.picturepublishing.service.ImageService;
import com.yeshtery.picturepublishing.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserService userService;

    private final ImageService imageService;

    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserService userService, ImageService imageService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.imageService = imageService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        imageService.saveAll(
                Image.builder()
                        .category(ImageCategory.MACHINE)
                        .status(FileStatus.ACCEPT)
                        .fileName("1.jpg")
                        .description("any")
                        .build()
                ,
                Image.builder()
                        .category(ImageCategory.MACHINE)
                        .status(FileStatus.ACCEPT)
                        .fileName("2.jpg")
                        .description("any 2")
                        .build()
                ,
                Image.builder()
                        .category(ImageCategory.MACHINE)
                        .status(FileStatus.UNPROCESSED)
                        .fileName("3.jpg")
                        .description("any")
                        .build()
                ,
                Image.builder()
                        .category(ImageCategory.MACHINE)
                        .status(FileStatus.UNPROCESSED)
                        .fileName("4.jpg")
                        .description("any")
                        .build()
        );


        userService.saveAdmin(
                Users.builder()
                        .email("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .build()
        );

        userService.saveUser(
                Users.builder()
                        .email("user")
                        .password(passwordEncoder.encode("user"))
                        .build()
        );


    }
}
