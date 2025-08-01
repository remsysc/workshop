package com.sysc.workshop.cart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sysc.workshop.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private  int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;



    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    public  void setTotalPrice(){
        this.totalPrice=  this.unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
