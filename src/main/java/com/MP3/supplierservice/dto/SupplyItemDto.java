package com.MP3.supplierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyItemDto {
    private int id;
    private String productName;
    private int supplyQuantity;
}
