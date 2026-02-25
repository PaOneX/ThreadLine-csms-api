package edu.icet.model.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"category", "variants"})
public class Product {
    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Category category;

    @Builder.Default
    private List<ProductVariant> variants = new ArrayList<>();

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
