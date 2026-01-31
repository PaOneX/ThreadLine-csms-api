package edu.icet.model.entity;

import edu.icet.util.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Date orderDate;
    private Status status;
    private Double orderAmount;

    @PrePersist
    public void orderDate(){
        orderDate = new Date();
    }

}
