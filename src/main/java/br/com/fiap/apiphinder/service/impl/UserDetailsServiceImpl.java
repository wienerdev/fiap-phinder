package br.com.fiap.apiphinder.service.impl;

import br.com.fiap.apiphinder.domain.entity.User;
import br.com.fiap.apiphinder.domain.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = repository.findByUsername(username);

        if (user == null) {
            throw new Exception(String.format("User does not exist, email: %s", username));
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}