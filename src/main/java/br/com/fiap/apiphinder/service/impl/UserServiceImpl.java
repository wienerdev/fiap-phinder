package br.com.fiap.apiphinder.service.impl;

import br.com.fiap.apiphinder.controller.dto.SignupRequest;
import br.com.fiap.apiphinder.domain.entity.User;
import br.com.fiap.apiphinder.domain.repository.UserRepository;
import br.com.fiap.apiphinder.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void signup(SignupRequest request) throws Exception {
        String username = request.username();
        User existingUser = repository.findByUsername(username);
        if (existingUser != null) {
            throw new Exception(String.format("User with the username '%s' already exists.", username));
        }

        String hashedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.username(), hashedPassword);
        repository.save(user);
    }
}
