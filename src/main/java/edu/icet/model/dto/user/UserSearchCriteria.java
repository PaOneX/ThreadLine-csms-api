package edu.icet.model.dto.user;

import lombok.Data;

@Data
public class UserSearchCriteria {
    private String username;
    private String email;
    private Boolean enabled;
    private String role;
}
