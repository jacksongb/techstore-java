package com.techstore.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private Long userId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double total;
    private String status = "待支付";
    private LocalDateTime createTime;
}
