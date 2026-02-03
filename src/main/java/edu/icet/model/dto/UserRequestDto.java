package edu.icet.model.dto;

import edu.icet.util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestDto {
    private String email;
    private String username;
    private String password;
    private Role role;
}
