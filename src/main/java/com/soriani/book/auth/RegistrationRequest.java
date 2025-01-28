package com.soriani.book.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotBlank(message = "First name is mandatory")
    @NotEmpty(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @NotEmpty(message = "Last name is mandatory")
    private String lastName;

    @Email(message = "Email is not formatted")
    @NotBlank(message = "Email is mandatory")
    @NotEmpty(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @NotEmpty(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long minimum")
    private String password;

}
