package com.training.product.repository;

import com.training.product.dto.CustomerRequest;
import com.training.product.entity.CustomerEntity;
import com.training.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByName(String name);
}
