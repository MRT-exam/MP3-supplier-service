package com.MP3.supplierservice.event;

import com.MP3.supplierservice.dto.ResupplyOrderLineDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResupplyDeliveryEvent {
    private List<ResupplyOrderLineDto> resupplyDeliveryItems;
}
