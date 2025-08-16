package com.sysc.workshop.core.security.jwt;

import com.sysc.workshop.core.security.user.SecurityUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthTokenFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils;
    private SecurityUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        @Nonnull HttpServletRequest request,
        @Nonnull HttpServletResponse response,
        @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {
                String username = jwtUtils.getUsernameFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(
                    username
                );
                Authentication auth = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response
                .getWriter()
                .write(
                    e.getMessage() +
                    " : Invalid or Expired Token, you may login and try again."
                );
            return;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (
            StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")
        ) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
