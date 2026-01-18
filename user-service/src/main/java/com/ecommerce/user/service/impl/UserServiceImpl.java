package com.ecommerce.user.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.user.dto.LoginRequestDto;
import com.ecommerce.user.dto.LoginResponseDto;
import com.ecommerce.user.dto.UserCreateRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.exception.ResourceAlreadyExistsException;
import com.ecommerce.user.exception.ResourceNotFoundException;
import com.ecommerce.user.mapper.AuthMapper;
import com.ecommerce.user.mapper.UserMapper;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepo;
  private final UserMapper userMapper;
  private final JwtUtil jwtUtil;
  private final AuthMapper authMapper;

  @Override
  public UserResponseDto register(UserCreateRequestDto dto) {
    if (userRepo.existsByEmail(dto.getEmail())) {
      throw new ResourceAlreadyExistsException(" Email already  registered ");
    }
    User user = userMapper.toEntity(dto);
    User savedUser = userRepo.save(user);
    return userMapper.toDto(savedUser);

  }

  @Override
  public LoginResponseDto login(LoginRequestDto dto) {
    User user = userRepo.findByEmail(dto.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));

    boolean matches = passwordEncoder.matches(dto.getPassword(), user.getPassword());

    if (!matches) {
      throw new ResourceNotFoundException("Invalid Credentials");

    }

    String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRoles());
    return authMapper.toLoginResponse(user, token);

  }

  public UserResponseDto getrById(Long id) {
    User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(" User not found with id " + id));
    return userMapper.toDto(user);
  }

}