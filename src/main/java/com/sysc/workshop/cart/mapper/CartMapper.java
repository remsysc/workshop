package com.sysc.workshop.cart.mapper;

import com.sysc.workshop.cart.dto.CartDTO;
import com.sysc.workshop.cart.model.Cart;
import com.sysc.workshop.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface CartMapper {
    @Mapping(source = "totalAmount", target = "totalAmount")
    CartDTO toDto(Cart cart);
}
