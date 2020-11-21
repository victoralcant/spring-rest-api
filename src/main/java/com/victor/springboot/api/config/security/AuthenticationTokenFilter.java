package com.victor.springboot.api.config.security;

import com.victor.springboot.api.config.security.TokenService;
import com.victor.springboot.model.Usuario;
import com.victor.springboot.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthenticationTokenFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        String token = recuperarToken(httpServletRequest);
        boolean isValidToken = tokenService.isValid(token);
        if (isValidToken) {
            authenticateUser(token);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void authenticateUser(String token) {
        Long userId = tokenService.getUserId(token);
        Optional<Usuario> userOptional = userRepository.findById(userId);
        Usuario user = userOptional.get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }

}
