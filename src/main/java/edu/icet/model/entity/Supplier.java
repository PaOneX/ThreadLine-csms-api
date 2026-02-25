package edu.icet.model.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
