package com.sysc.workshop.cart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sysc.workshop.user.model.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private Long version;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(
        mappedBy = "cart",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;

    // add item
    // remove item
    // update total amount

    public void addItem(CartItem item) {
        cartItems.add(item);
        item.setCart(this);
        updateTotalAmount();
    }

    public void removeItem(CartItem item) {
        cartItems.remove(item);
        item.setCart(null);
        updateTotalAmount();
    }

    public void updateTotalAmount() {
        totalAmount = cartItems
            .stream()
            .map(item -> {
                BigDecimal unitPrice = item.getUnitPrice();
                if (unitPrice == null) {
                    return BigDecimal.ZERO;
                }
                return unitPrice.multiply(
                    BigDecimal.valueOf(item.getQuantity())
                );
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
