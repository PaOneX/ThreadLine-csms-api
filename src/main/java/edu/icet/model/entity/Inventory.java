package edu.icet.model.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"variant"})
public class Inventory {
    private Long id;

    private ProductVariant variant;

    private Integer quantity;

    private LocalDateTime lastRestocked;

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
