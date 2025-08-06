package com.sysc.workshop.user.mapper;

import com.sysc.workshop.user.UserDto;
import com.sysc.workshop.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
