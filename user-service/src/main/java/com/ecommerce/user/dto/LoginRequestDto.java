package com.ecommerce.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoginRequestDto {
  @jakarta.validation.constraints.Email(message = " valid email required ")
  @NotBlank(message = " email required ")
  private String email;
  @NotBlank(message = " password required ")
  private String password;

}
