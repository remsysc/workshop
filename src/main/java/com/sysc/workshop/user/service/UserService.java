package com.sysc.workshop.user.service;

import com.sysc.workshop.cart.model.Cart;
import com.sysc.workshop.core.role.Role;
import com.sysc.workshop.core.role.RoleRepository;
import com.sysc.workshop.user.UserDto;
import com.sysc.workshop.user.exception.EmailAlreadyExists;
import com.sysc.workshop.user.exception.UserNotFoundException;
import com.sysc.workshop.user.mapper.UserMapper;
import com.sysc.workshop.user.model.User;
import com.sysc.workshop.user.repository.UserRepository;
import com.sysc.workshop.user.request.CreateUserRequest;
import com.sysc.workshop.user.request.UpdateUserRequest;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDto createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExists("Email already exists");
        }

        User user = new User(
            request.getName(),
            request.getEmail(),
            passwordEncoder.encode(request.getPassword())
        );

        Role defaultRole = roleRepository
            .findByName("ROLE_USER")
            .orElseThrow(() -> new RuntimeException("Defaul role not found"));

        user.getRoles().add(defaultRole);
        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public User getUserEntityById(UUID userId) {
        return userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User Not Found!"));
    }

    @Override
    public UserDto getUserById(UUID userId) {
        return userMapper.toDto(
            userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found!"))
        );
    }

    @Override
    public void deleteUserById(UUID userId) {
        userRepository.delete(getUserEntityById(userId));
    }

    @Override
    public UserDto updateUserById(UpdateUserRequest request, UUID userId) {
        User user = getUserEntityById(userId);

        if (userRepository.existsByEmailAndIdNot(request.getEmail(), userId)) {
            throw new EmailAlreadyExists("Email already in use");
        }
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
