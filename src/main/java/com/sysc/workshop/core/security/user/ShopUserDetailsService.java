package com.sysc.workshop.core.security.user;

import com.sysc.workshop.user.exception.UserNotFoundException;
import com.sysc.workshop.user.model.User;
import com.sysc.workshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(()-> new UserNotFoundException("Email Not Exist"));
        return ShopUserDetails.buildUserDetails(user);


    }
}
