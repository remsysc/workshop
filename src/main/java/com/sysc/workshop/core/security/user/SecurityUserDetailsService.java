package com.sysc.workshop.core.security.user;

import com.sysc.workshop.user.model.User;
import com.sysc.workshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//Purpose: Given an email, fetch the User from the database and return a UserDetails.
// When it runs: Invoked by Spring’s authentication flow
// (e.g., during form login, basic auth, or JWT validation depending on your setup).

public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {
        // User user = Optional.ofNullable(
        //     userRepository.findByEmail(email)
        // ).orElseThrow(() -> new UserNotFoundException("Email Not Exist"));
        User user = userRepository.findByEmail(email); // global exception already catch this
        return SecurityUserDetails.buildUserDetails(user);
    }
}
