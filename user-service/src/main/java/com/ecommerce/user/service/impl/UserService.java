package com.ecommerce.user.service.impl;

import com.ecommerce.user.dto.LoginRequestDto;
import com.ecommerce.user.dto.UserCreatedRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.entity.User;

public interface UserService {

  UserResponseDto register(UserCreatedRequestDto dto);

  LoginRequestDto login(LoginRequestDto dto);

  UserResponseDto getrById(Long id);

}
