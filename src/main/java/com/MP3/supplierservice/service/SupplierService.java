package com.MP3.supplierservice.service;

import com.MP3.supplierservice.dto.SupplyItemDto;
import com.MP3.supplierservice.event.ResupplyEvent;
import com.MP3.supplierservice.model.Supply;
import com.MP3.supplierservice.model.SupplyItem;
import com.MP3.supplierservice.repository.SupplierRepository;
import enums.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    public Supply createSupply(ResupplyEvent resupplyEvent) {
        Supply supply = new Supply();
        supply.setSupplyNumber(UUID.randomUUID().toString());
        Stream<SupplyItemDto> supplyItemDtoStream = resupplyEvent.getSupplyItemDtos().stream();

        List<SupplyItem> supplyItems = supplyItemDtoStream
                .map(this::mapFromDto)
                        .toList();
        supply.setSupplyItems(supplyItems);

        supplierRepository.save(supply);
        return supply;
    }

    public void removeSupplyItemsAlreadyOrdered(ResupplyEvent resupplyEvent) {

        // Retrieves the supplies from the DB with status ORDERED
        List<Supply> suppliesNotDelivered = supplierRepository.findAll()
                .stream()
                .filter(supply -> supply.getStatus() == DeliveryStatus.ORDERED)
                .toList();

        // Retrieves the supplyItemIds in all suppliesNotDelivered
        List<Integer> supplyItemIdsNotDelivered = new ArrayList<>();
        addChildren(suppliesNotDelivered, supplyItemIdsNotDelivered);

        resupplyEvent.getSupplyItemDtos()
                .stream()
                .filter(supplyItemDto -> supplyItemIdsNotDelivered
                        .contains(supplyItemDto.getId()))
                .toList();
    }

    private void addChildren(List<Supply> supplies, List<Integer> supplyItemIds) {
        if (supplies != null && supplies.size() > 0) {
            for (Supply supply: supplies) {
                supplyItemIds.addAll(supply.getSupplyItems()
                        .stream()
                        .map(SupplyItem::getId)
                        .toList());
            }
        }
    }
    private SupplyItem mapFromDto(SupplyItemDto supplyItemDto) {
        SupplyItem supplyItem = new SupplyItem();
        supplyItem.setProductName(supplyItemDto.getProductName());
        supplyItem.setSupplyQuantity(supplyItem.getSupplyQuantity());
        return supplyItem;
    }
}
