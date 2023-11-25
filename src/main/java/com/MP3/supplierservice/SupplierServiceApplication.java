package com.MP3.supplierservice;

import com.MP3.supplierservice.dto.ResupplyOrderLineDto;
import com.MP3.supplierservice.event.RequestResupplyEvent;
import com.MP3.supplierservice.model.ResupplyOrder;
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
	public static void main(String[] args) {
		SpringApplication.run(SupplierServiceApplication.class, args);
	}

}
