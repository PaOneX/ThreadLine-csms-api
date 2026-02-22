package edu.icet.model.dto.auth;

//import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

//    @NotBlank(message = "refreshToken is required")
    private String refreshToken;
}
