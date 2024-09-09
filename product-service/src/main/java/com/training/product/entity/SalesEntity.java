package com.training.product.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "SALES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SalesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product ID can't be null, empty or blank")
    private Long productId;

    @NotBlank(message = "Customer ID can't be null, empty or blank")
    private Long customerId;

    @NotBlank(message = "Invoice number can't be null, empty or blank")
    private String invoiceNumber;

    @Min(value = 1, message="Total price must be greater than 0")
    private Double totalPrice;

    @Min(value = 1, message="Payment received must be greater than 0")
    private Double paymentReceived;

    @NotBlank(message = "Change can't be null, empty or blank")
    private Double change;

    @Min(value = 1, message="Quantity must be greater than 0")
    private int quantity;

    @CreationTimestamp
    private LocalDateTime salesDate;

}
