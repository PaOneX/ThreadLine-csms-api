package edu.icet.model.dto.user;

//import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRolesRequest {

//    @NotEmpty(message = "roles are required")
    private Set<String> roles;
}
