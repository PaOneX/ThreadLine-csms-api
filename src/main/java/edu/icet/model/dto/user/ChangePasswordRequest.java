package edu.icet.model.dto.user;

//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for password change.
 * Requires current password for security verification.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    //    @NotBlank(message = "Current password is required")
    private String currentPassword;

    //    @NotBlank(message = "New password is required")
//    @Size(min = 6, max = 72, message = "password must be between 6 and 72 characters")
    private String newPassword;
}
