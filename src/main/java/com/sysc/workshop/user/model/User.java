package com.sysc.workshop.user.model;

import com.sysc.workshop.cart.model.Cart;
import com.sysc.workshop.core.role.Role;
import com.sysc.workshop.order.model.Order;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @NaturalId
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt;

    // 1 user can have many orders
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

   @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private  Cart cart;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @Builder.Default
    private Collection<Role> roles = new HashSet<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }



    @PrePersist
    protected void onCreated(){
        this.createdAt = LocalDateTime.now();
    }

}
