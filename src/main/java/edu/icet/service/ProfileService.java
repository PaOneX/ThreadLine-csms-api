package edu.icet.service;

import edu.icet.model.dto.user.ChangePasswordRequest;
import edu.icet.model.dto.user.UpdateProfileRequest;
import edu.icet.model.dto.user.UserDto;

/**
 * Service for user self-service profile operations.
 * These are operations users perform on their own account.
 */
public interface ProfileService {

    /**
     * Get current user's profile.
     */
    UserDto getCurrentUserProfile(String username);

    /**
     * Update current user's profile (fullName, etc.).
     */
    UserDto updateProfile(String username, UpdateProfileRequest request);

    /**
     * Change current user's password.
     * Requires current password verification.
     */
    void changePassword(String username, ChangePasswordRequest request);
}
