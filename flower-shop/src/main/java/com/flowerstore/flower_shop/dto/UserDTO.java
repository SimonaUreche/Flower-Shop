package com.flowerstore.flower_shop.dto;

import com.flowerstore.flower_shop.constants.UserType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private UserType role;
}
