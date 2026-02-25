package edu.icet.model.dto.user;

import edu.icet.model.enums.Gender;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for users updating their own profile.
 * Only contains fields users can modify themselves.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {

    @Size(max = 100, message = "full name is too long")
    private String fullName;

    private Gender gender;
}
