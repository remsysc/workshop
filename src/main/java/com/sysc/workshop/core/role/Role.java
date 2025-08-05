package com.sysc.workshop.core.role;

import com.sysc.workshop.user.model.User;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> user = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }
}
