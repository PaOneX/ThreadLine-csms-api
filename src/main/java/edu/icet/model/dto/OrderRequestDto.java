package edu.icet.model.dto;

import edu.icet.util.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequestDto {
    private Long userId;
    private Long orderItemId;
    private String orderItemName;
    private Status status;
    private Double orderAmount;
}
