package com.MP3.supplierservice.repository;

import com.MP3.supplierservice.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supply, Integer> {
}
