package com.MP3.supplierservice.producer;

import com.MP3.supplierservice.event.ResupplyDeliveryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResupplyDeliveryProducer {
    private KafkaTemplate<String, ResupplyDeliveryEvent> kafkaTemplate;

    public void produce(ResupplyDeliveryEvent event) {
        kafkaTemplate.send("resupplyDeliveryTopic", event);
    }
}
