package com.sysc.workshop.cart.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class AddItemRequestDTO {

    @NotNull
    private UUID cartId;

    @NotNull
    private UUID productId;

    @NotNull
    private Integer quantity;
}
