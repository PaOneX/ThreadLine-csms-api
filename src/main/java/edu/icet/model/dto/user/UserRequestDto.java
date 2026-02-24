package edu.icet.model.dto.user;

//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestDto {

    //    @NotBlank(message = "username is required")
//    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
    private String username;

    //    @NotBlank(message = "email is required")
//    @Email(message = "invalid email format")
//    @Size(max = 254, message = "email is too long")
    private String email;

    //    @NotBlank(message = "password is required")
//    @Size(min = 8, max = 72, message = "password must be between 8 and 72 characters")
    private String password;

    private Set<String> roles;
}
