package com.sysc.workshop.core.auth;

import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.core.security.jwt.JwtUtils;
import com.sysc.workshop.core.security.user.SecurityUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(
        @Valid @RequestBody LoginRequest loginRequest
    ) {
        // Implementation for login

        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtUtils.generateTokenForUser(auth);
        SecurityUserDetails userDetails =
            (SecurityUserDetails) auth.getPrincipal();
        JwtResponse jwtResponse = new JwtResponse(
            userDetails.getId(),
            "Bearer",
            jwt
        );
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Login successfull", jwtResponse)
        );
    }

    public record JwtResponse(
        java.util.UUID userId,
        String tokenType,
        String accessToken
    ) {}
}
