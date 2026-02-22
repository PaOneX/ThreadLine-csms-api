package edu.icet.model.dto.user;

import edu.icet.model.enums.Gender;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateUserRequest {

//    @NotBlank(message = "Username is required")
//    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
    private String username;

//    @NotBlank(message = "Email is required")
//    @Email(message = "invalid email format")
//    @Size(max = 254, message = "email is too long")
    private String email;

//    @Size(max = 100, message = "full name is too long")
    private String fullName;

    private Gender gender;

//    @NotBlank(message = "Password is required")
//    @Size(min   = 6, max = 72, message = "password must be between 6 and 72 characters")
    private String password;

//    @NotEmpty(message =          "roles are required")
    private Set<String> roles;
}
