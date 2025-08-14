package com.sysc.workshop.cart.dto;

import com.sysc.workshop.cart.model.CartItem;
import com.sysc.workshop.user.UserDto;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class CartDTO {

    private BigDecimal totalAmount;
    private Set<CartItem> cartItems = new HashSet<>();
    UserDto user;
}
