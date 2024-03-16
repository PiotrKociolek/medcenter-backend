package com.example.service;

import com.example.repository.UserRepository;
import com.example.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SecurityDetailsService implements UserDetailsService {
    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserEntity byUsername = repo.findByEmail(username).get();

        return new User(byUsername.getEmail(), byUsername.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_" + byUsername.getPermissionType())));
    }
}
