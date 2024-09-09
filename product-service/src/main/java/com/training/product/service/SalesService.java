package com.training.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.product.constant.Constant;
import com.training.product.dto.*;
import com.training.product.entity.SalesEntity;
import com.training.product.repository.SalesRepository;
import com.training.product.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final ObjectMapper mapper;
    private final ResponseHelper responseHelper;

    // Menyimpan data sales
    public void saveSales(SalesEntity sales) {
        salesRepository.save(sales);
    }

    // Mendapatkan semua sales
    public ResponseEntity<ApiResponse> getAllSales() {
        List<SalesEntity> sales = salesRepository.findAll();

        List<SalesResponse> response = sales.stream()
                .map(sale -> SalesResponse.builder()
                        .productId(sale.getProductId())
                        .customerId(sale.getCustomerId())
                        .invoiceNumber(sale.getInvoiceNumber())
                        .totalPrice(sale.getTotalPrice())
                        .paymentReceived(sale.getPaymentReceived())
                        .change(sale.getChange())
                        .quantity(sale.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return responseHelper.setResponse(
                HttpStatus.OK,
                Constant.SUCCESS,
                "Success get all sales data",
                response);
    }

    // Mendapatkan data sales berdasarkan ID
    public ResponseEntity<ApiResponse> getSalesById(Long id) {
        Optional<SalesEntity> salesFromDb = salesRepository.findById(id);

        if (salesFromDb.isEmpty()) {
            return responseHelper.setResponse(
                    HttpStatus.NOT_FOUND,
                    Constant.NOT_FOUND,
                    "Sales with ID: " + id + " not found",
                    null);
        }

        SalesResponse response = mapper.convertValue(salesFromDb.get(), SalesResponse.class);
        return responseHelper.setResponse(
                HttpStatus.OK,
                Constant.SUCCESS,
                "Success get sales with ID: " + id,
                response);
    }

    // Menambah data sales baru
    public ResponseEntity<ApiResponse> addSales(SalesRequest request) {
        SalesEntity salesEntity = mapper.convertValue(request, SalesEntity.class);
        this.saveSales(salesEntity);

        return responseHelper.setResponse(
                HttpStatus.CREATED,
                Constant.CREATED,
                "Success added new sales data",
                null);
    }

    // Mengedit data sales berdasarkan ID
    public ResponseEntity<ApiResponse> editSales(Long id, SalesRequest request) {
        Optional<SalesEntity> salesFromDb = salesRepository.findById(id);

        if (salesFromDb.isEmpty()) {
            return responseHelper.setResponse(
                    HttpStatus.NOT_FOUND,
                    Constant.NOT_FOUND,
                    "Sales with ID: " + id + " not found",
                    null);
        }

        SalesEntity editedSales = salesFromDb.get();
        editedSales.setProductId(request.getProductId());
        editedSales.setCustomerId(request.getCustomerId());
        editedSales.setInvoiceNumber(request.getInvoiceNumber());
        editedSales.setTotalPrice(request.getTotalPrice());
        editedSales.setPaymentReceived(request.getPaymentReceived());
        editedSales.setChange(request.getChange());
        editedSales.setQuantity(request.getQuantity());
        this.saveSales(editedSales);

        return responseHelper.setResponse(
                HttpStatus.CREATED,
                Constant.SUCCESS,
                "Success updated sales data",
                null);
    }

    // Menghapus data sales berdasarkan ID
    public ResponseEntity<ApiResponse> deleteSales(Long id) {
        boolean isExist = salesRepository.existsById(id);

        if (!isExist) {
            return responseHelper.setResponse(
                    HttpStatus.NOT_FOUND,
                    Constant.NOT_FOUND,
                    "Sales with ID: " + id + " not found",
                    null);
        }

        salesRepository.deleteById(id);
        return responseHelper.setResponse(
                HttpStatus.OK,
                Constant.SUCCESS,
                "Success deleted sales data",
                null);
    }

    // Mengupdate data sales dengan permintaan spesifik
    public ResponseEntity<ApiResponse> updateSalesData(UpdateSalesDataRequest request) {
        Optional<SalesEntity> salesFromDb = salesRepository.findById(request.getProductId());

        if (salesFromDb.isPresent()) {
            SalesEntity sales = salesFromDb.get();
            sales.setCustomerId(request.getCustomerId());
            sales.setInvoiceNumber(request.getInvoiceNumber());
            sales.setTotalPrice(request.getTotalPrice());
            sales.setPaymentReceived(request.getPaymentReceived());
            sales.setChange(request.getChange());
            sales.setQuantity(request.getQuantity());
            this.saveSales(sales);

            UpdateSalesDataResponse response = mapper.convertValue(sales, UpdateSalesDataResponse.class);

            return responseHelper.setResponse(
                    HttpStatus.OK,
                    Constant.SUCCESS,
                    "Success updated sales data",
                    response);
        }

        return responseHelper.setResponse(
                HttpStatus.NOT_FOUND,
                Constant.NOT_FOUND,
                "Sales with product ID: " + request.getProductId() + " not found",
                null);
    }
}
