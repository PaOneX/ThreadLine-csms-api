package edu.icet.model.entity;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    private Long id;

    private String token;

    private User user;

    private Instant expiryDate;
}
