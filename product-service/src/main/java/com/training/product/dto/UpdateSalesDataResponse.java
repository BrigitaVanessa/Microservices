package com.training.product.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateSalesDataResponse {
    private Long productId;
    private Long customerId;
    private String invoiceNumber;
    private Double totalPrice;
    private Double paymentReceived;
    private Double change;
    private int quantity;
}
