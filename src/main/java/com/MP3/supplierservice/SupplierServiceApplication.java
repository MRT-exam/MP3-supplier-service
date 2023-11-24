package com.MP3.supplierservice;

import com.MP3.supplierservice.dto.SupplyItemDto;
import com.MP3.supplierservice.event.DeliveryEvent;
import com.MP3.supplierservice.event.ResupplyEvent;
import com.MP3.supplierservice.event.SupplyCreatedEvent;
import com.MP3.supplierservice.model.Supply;
import com.MP3.supplierservice.model.SupplyItem;
import com.MP3.supplierservice.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class SupplierServiceApplication {
	private final SupplierService supplierService;
	private final KafkaTemplate<String, SupplyCreatedEvent> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SupplierServiceApplication.class, args);
	}

	// Consumes Resupply Events created by Inventory Service
	@KafkaListener(topics = "inventoryResupplyTopic")
	public void handleResupply(ResupplyEvent resupplyEvent) {
		// TODO: Error Handling
		log.info("Resupply Event Received");
		supplierService.removeSupplyItemsAlreadyOrdered(resupplyEvent);
		Supply supply = supplierService.createSupply(resupplyEvent);
		// Send kafka event with supplyNumber and inform that it was created


	}
	// Update the delivery status of the supply with the supplyNumber extracted
	// from the deliveryEvent
	@KafkaListener(topics = "inventoryDeliveryTopic")
	public void handleDelivery(DeliveryEvent deliveryEvent) {
		// Implement method in supplier service and call here
		// it should send all the supplyItems so inventory can extract quantity
		// and update accordingly
	}
}
