package edu.icet.model.dto;

import edu.icet.util.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrdersDto {
 private Long id;
 private Long userId;
 private Date orderDate;
 private Status status;
 private Double orderAmount;
}
