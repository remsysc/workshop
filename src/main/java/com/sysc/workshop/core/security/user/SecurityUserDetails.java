package com.sysc.workshop.core.security.user;

import com.sysc.workshop.user.model.User;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//Spring Security uses this object during authentication to check credentials and authorities.

//Purpose: Wraps your domain User into what Spring Security understands
//  (username, password, authorities).
// Authorities: Converts each Role into a SimpleGrantedAuthority.
// Spring Security checks these for access decisions.

public class SecurityUserDetails implements UserDetails {

    private UUID id;
    private String email;
    private String password;

    private Collection<GrantedAuthority> authorities;

    public static SecurityUserDetails buildUserDetails(User user) {
        List<GrantedAuthority> authorities = user
            .getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
        return new SecurityUserDetails(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            Collections.unmodifiableList(authorities) //should never be modified once built
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
