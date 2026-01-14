package com.ecommerce.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.user.dto.LoginRequestDto;
import com.ecommerce.user.dto.UserCreatedRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepo;

  @Override
  public UserResponseDto register(UserCreatedRequestDto dto) {
    if (userRepo.existsByEmail(dto.getEmail())) {
      throw new RuntimeException(" User already exists with email " + dto.getEmail());
    }
    User user = new User();
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword());
    User saveduser = userRepo.save(user);
    return new UserResponseDto();

  }

  @Override
  public LoginRequestDto login(LoginRequestDto dto) {

    return null;
  }

  public UserResponseDto getrById(Long id) {
    return null;
  }

}
