package com.backend.service.impl;

import com.backend.dto.request.RegisterRequest;
import com.backend.dto.response.FindUserByEmailResponse;
import com.backend.dto.request.UpdateUserPasswordRequest;
import com.backend.dto.response.UserResponse;
import com.backend.model.enums.Role;
import com.backend.model.entity.User;
import com.backend.exception.UserNotFoundException;
import com.backend.repository.UserRepository;
import com.backend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse save(RegisterRequest request) {
        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.USER
        );

        User savedUser = userRepository.save(user);
        return UserResponse.fromEntity(savedUser);
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    @Override
    public Optional<FindUserByEmailResponse> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(FindUserByEmailResponse::fromEntity);
    }

    @Override
    public void delete(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }

    @Override
    public UserResponse updatePassword(UUID id, UpdateUserPasswordRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setPassword(passwordEncoder.encode(request.password()));

        User updated = userRepository.save(user);
        return UserResponse.fromEntity(updated);
    }
}
