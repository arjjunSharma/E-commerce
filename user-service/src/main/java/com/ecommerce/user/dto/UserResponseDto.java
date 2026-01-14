package com.ecommerce.user.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserResponseDto {
  private Long id;
  private String name;
  private String email;
  private String phone;
  private String roles;
  private String createdAt;
  private String updatedAt;

}
