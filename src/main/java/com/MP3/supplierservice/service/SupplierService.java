package com.MP3.supplierservice.service;

import com.MP3.supplierservice.dto.ResupplyOrderLineDto;
import com.MP3.supplierservice.event.RequestResupplyEvent;
import com.MP3.supplierservice.event.ResupplyDeliveryEvent;
import com.MP3.supplierservice.model.ResupplyOrder;
import com.MP3.supplierservice.model.ResupplyOrderLine;
import com.MP3.supplierservice.producer.ResupplyDeliveryProducer;
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
    private final ResupplyDeliveryProducer resupplyDeliveryProducer;
    public void createAndSendResupplyOrder(List<ResupplyOrderLineDto> productsToResupplyDtos) throws InterruptedException {
        ResupplyOrder resupplyOrder = new ResupplyOrder();
        resupplyOrder.setSupplyNumber(UUID.randomUUID().toString());

        List<ResupplyOrderLine> resupplyOrderLines = productsToResupplyDtos
                .stream()
                .map(this::mapFromDto)
                .toList();
        // Removes resupplyOrderLines already ordered for restock
        removeOrderLinesAlreadyOrdered(resupplyOrderLines);
        resupplyOrder.setResupplyOrderLines(resupplyOrderLines);
        // TODO: Check if there are any orderlines otherwise cancel resupplyOrder creation
        supplierRepository.save(resupplyOrder);
        // Waiting for delivery
        Thread.sleep(5000);
        ResupplyDeliveryEvent resupplyDeliveryEvent = new ResupplyDeliveryEvent();
        resupplyDeliveryEvent.setResupplyDeliveryItems(resupplyOrderLines
                .stream()
                .map(this::mapToDto)
                .toList());
        // Send delivery to inventory service and update ResupplyOrder's Status
        resupplyDeliveryProducer.produce(resupplyDeliveryEvent);
        resupplyOrder.setStatus(DeliveryStatus.DELIVERED);
        supplierRepository.save(resupplyOrder);
    }

    private void removeOrderLinesAlreadyOrdered(List<ResupplyOrderLine> resupplyOrderLines) {
        List<ResupplyOrder> resupplyOrdersNotDelivered = supplierRepository.findAll()
                .stream()
                .filter(resupplyOrder -> resupplyOrder.getStatus() == DeliveryStatus.ORDERED)
                .toList();

        List<Integer> resupplyOrderLinesIdsToRemove = new ArrayList<>();
        addChildrenIdsToList(resupplyOrdersNotDelivered, resupplyOrderLinesIdsToRemove);

        resupplyOrderLines.removeIf(resupplyOrderLine ->
                resupplyOrderLinesIdsToRemove.contains(resupplyOrderLine.getId()));
    }


    private void addChildrenIdsToList(List<ResupplyOrder> resupplyOrders, List<Integer> resupplyOrderLineIds) {
        if (resupplyOrders != null && resupplyOrders.size() > 0) {
            for (ResupplyOrder resupplyOrder : resupplyOrders) {
                resupplyOrderLineIds.addAll(resupplyOrder.getResupplyOrderLines()
                        .stream()
                        .map(ResupplyOrderLine::getId)
                        .toList());
            }
        }
    }
    private ResupplyOrderLine mapFromDto(ResupplyOrderLineDto resupplyOrderLineDto) {
        ResupplyOrderLine resupplyOrderLine = new ResupplyOrderLine();
        resupplyOrderLine.setId(resupplyOrderLineDto.getId());
        resupplyOrderLine.setProductName(resupplyOrderLineDto.getProductName());
        resupplyOrderLine.setSupplyQuantity(resupplyOrderLine.getSupplyQuantity());
        return resupplyOrderLine;
    }

    private ResupplyOrderLineDto mapToDto(ResupplyOrderLine resupplyOrderLine) {
        ResupplyOrderLineDto resupplyOrderLineDto = new ResupplyOrderLineDto();
        resupplyOrderLineDto.setId(resupplyOrderLine.getId());
        resupplyOrderLineDto.setProductName(resupplyOrderLine.getProductName());
        resupplyOrderLineDto.setResupplyQuantity(resupplyOrderLine.getSupplyQuantity());
        return resupplyOrderLineDto;
    }
}
