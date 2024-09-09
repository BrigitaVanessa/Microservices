package com.training.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesResponse {
    private Long productId;
    private Long customerId;
    private String invoiceNumber;
    private Double totalPrice;
    private Double paymentReceived;
    private Double change;
    private int quantity;
}
