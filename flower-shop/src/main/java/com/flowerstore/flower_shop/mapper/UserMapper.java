package com.flowerstore.flower_shop.mapper;

import com.flowerstore.flower_shop.dto.UserDTO;
import com.flowerstore.flower_shop.model.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRole()
        );
    }

    public static User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .role(dto.getRole())
                .build();
    }
}

//@Mapper(componentModel = "spring")
//public interface UserMapper {
//    UserDTO toDTO(User user);
//    User toEntity(UserDTO userDTO);
//}

