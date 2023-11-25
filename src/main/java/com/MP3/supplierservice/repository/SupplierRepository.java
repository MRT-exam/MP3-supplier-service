package com.MP3.supplierservice.repository;

import com.MP3.supplierservice.model.ResupplyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<ResupplyOrder, Integer> {
    ResupplyOrder findBySupplyNumber(String supplyNumber);
}
