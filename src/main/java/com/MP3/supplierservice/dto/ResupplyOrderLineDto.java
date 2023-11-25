package com.MP3.supplierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResupplyOrderLineDto {
    private int id;
    private String productName;
    private int resupplyQuantity;
}
