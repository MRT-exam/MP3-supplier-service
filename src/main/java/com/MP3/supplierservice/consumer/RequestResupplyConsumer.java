package com.MP3.supplierservice.consumer;

import com.MP3.supplierservice.dto.ResupplyOrderLineDto;
import com.MP3.supplierservice.event.RequestResupplyEvent;
import com.MP3.supplierservice.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RequestResupplyConsumer {
    private final SupplierService supplierService;

    @KafkaListener(groupId = "supplierService", topics = "inventoryResupplyTopic")
    public void consume(RequestResupplyEvent event) throws InterruptedException {
        // Call ProductService and update quantities in stock in DB
        List<ResupplyOrderLineDto> productsToResupplyDtos = event.getResupplyOrderLineDtos();
        supplierService.createAndSendResupplyOrder(productsToResupplyDtos);
    }
}
