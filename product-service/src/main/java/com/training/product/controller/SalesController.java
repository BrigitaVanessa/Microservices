package com.training.product.controller;

import com.training.product.dto.ApiResponse;
import com.training.product.dto.SalesRequest;
import com.training.product.dto.UpdateSalesDataRequest;
import com.training.product.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SalesController {
    private final SalesService salesService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllSales() {
        return salesService.getAllSales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getSalesById(@PathVariable Long id) {
        return salesService.getSalesById(id);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addSales(@RequestBody SalesRequest request) {
        return salesService.addSales(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editSales(
            @PathVariable Long id,
            @RequestBody SalesRequest request) {
        return salesService.editSales(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSales(@PathVariable Long id) {
        return salesService.deleteSales(id);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse> updateSales(@RequestBody UpdateSalesDataRequest request) {
        return salesService.updateSalesData(request);
    }
}
