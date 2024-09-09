package com.training.product.repository;

import com.training.product.entity.CustomerEntity;
import com.training.product.entity.SalesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesRepository extends JpaRepository<SalesEntity, Long> {
    Optional<SalesEntity> findByName(String name);
}
