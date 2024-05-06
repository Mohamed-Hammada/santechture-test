package com.santechture.api.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest  implements Serializable {

    @Email(message = "user.invalid.email")
    @NotBlank(message = "user.email.required")
    private String email;
    @NotBlank(message = "user.name.required")
    private String username;


}
