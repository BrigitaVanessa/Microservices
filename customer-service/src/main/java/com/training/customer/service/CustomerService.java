package com.training.customer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.customer.constant.Constant;
import com.training.customer.dto.ApiResponse;
import com.training.customer.dto.CustomerRequest;
import com.training.customer.dto.CustomerResponse;
import com.training.customer.dto.UpdateCustomerDataRequest;
import com.training.customer.entity.CustomerEntity;
import com.training.customer.repository.CustomerRepository;
import com.training.customer.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ObjectMapper mapper;
    private final ResponseHelper responseHelper;

    public void saveCustomer(CustomerEntity customer){
        customerRepository.save(customer);
    }

    public ResponseEntity<ApiResponse> getCustomerById(Long id){
        Optional<CustomerEntity> customerFromDb = customerRepository.findById(id);

        if(customerFromDb.isEmpty()) {
            return responseHelper.setResponse(
                    HttpStatus.NOT_FOUND,
                    Constant.NOT_FOUND,
                    "Customer with ID: " + id + " not found",
                    null
            );
        }
        CustomerResponse response = mapper.convertValue(customerFromDb.get(), CustomerResponse.class);
        return responseHelper.setResponse(
                HttpStatus.OK,
                Constant.SUCCESS,
                "Success for Customer with ID: " + id,
                response
        );
    }

    public ResponseEntity<ApiResponse> getAllCustomers(){
        List<CustomerEntity> customers = customerRepository.findAll();

        List<CustomerResponse> response = customers.stream().map(
                        customer -> CustomerResponse.builder()
                                .name(customer.getName())
                                .email(customer.getEmail())
                                .phoneNumber(customer.getPhoneNumber())
                                .address(customer.getAddress())
                                .build())
                .collect(Collectors.toList());

        return responseHelper.setResponse(
                HttpStatus.OK,
                Constant.SUCCESS,
                "Success",
                response);
    }

    public ResponseEntity<ApiResponse> addCustomer(CustomerRequest request){

        CustomerEntity customerEntity = mapper.convertValue(request, CustomerEntity.class);
        this.saveCustomer(customerEntity);

        return responseHelper.setResponse(
                HttpStatus.CREATED,
                Constant.CREATED,
                "Success added new customer",
                null);
    }

    public ResponseEntity<ApiResponse> editCustomer(Long id, CustomerRequest request){

        Optional<CustomerEntity> customerFromDb = customerRepository.findById(id);

        if(customerFromDb.isEmpty()) {
            return responseHelper.setResponse(
                    HttpStatus.NOT_FOUND,
                    Constant.NOT_FOUND,
                    "Customer with ID: " + id + " not found",
                    null
            );
        }

        CustomerEntity editedCustomer = customerFromDb.get();
        editedCustomer.setName(request.getName());
        editedCustomer.setEmail(request.getEmail());
        editedCustomer.setPhoneNumber(request.getPhoneNumber());
        editedCustomer.setAddress(request.getAddress());
        editedCustomer.setUpdatedDate(LocalDateTime.now());
        this.saveCustomer(editedCustomer);

        return responseHelper.setResponse(
                HttpStatus.CREATED,
                Constant.SUCCESS,
                "Success updated data customer",
                null);
    }

    public ResponseEntity<ApiResponse> deleteCustomer (Long id) {

        boolean isExist = customerRepository.existsById(id);

        if (!isExist) {
            return responseHelper.setResponse(
                    HttpStatus.NOT_FOUND,
                    Constant.NOT_FOUND,
                    "Customer with ID: " + id + " not found",
                    null);
        }
        customerRepository.deleteById(id);
        return responseHelper.setResponse(
                HttpStatus.OK,
                Constant.SUCCESS,
                "Success deleted customer data",
                null);
    }

    public ResponseEntity<ApiResponse> updateCustomerData(UpdateCustomerDataRequest request){
        Optional<CustomerEntity> customerFromDb = customerRepository.findByName(request.getName());

        if(customerFromDb.isPresent()){
            CustomerEntity customer = customerFromDb.get();

            customer.setName(request.getName());
            customer.setEmail(request.getEmail());
            customer.setPhoneNumber(request.getPhoneNumber());
            customer.setAddress(request.getAddress());
            customer.setUpdatedDate(LocalDateTime.now());
            this.saveCustomer(customer);

            return responseHelper.setResponse(
                    HttpStatus.OK,
                    Constant.SUCCESS,
                    "Success updated customer data",
                    null
            );
        }

        return responseHelper.setResponse(
                HttpStatus.NOT_FOUND,
                Constant.NOT_FOUND,
                "Customer with ID: " + request.getName() + " not found",
                null);
    }

}
