package com.MP3.supplierservice.event;

import com.MP3.supplierservice.dto.SupplyItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResupplyEvent {
    private List<SupplyItemDto> supplyItemDtos;
}
