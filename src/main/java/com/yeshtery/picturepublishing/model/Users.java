package com.yeshtery.picturepublishing.model;


import com.yeshtery.picturepublishing.enums.Authority;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotEmpty
    @Column(unique = true)
    private String email;

    @NotNull @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Builder.Default
    private Authority authority = Authority.USER;
}
