package com.flowerstore.flower_shop.model;

import com.flowerstore.flower_shop.constants.UserType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class User { //trebuie sa adaug si parola/cum sa procedez cu parola?
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private UserType role; // CLIENT, ADMIN, LIVRATOR
}
