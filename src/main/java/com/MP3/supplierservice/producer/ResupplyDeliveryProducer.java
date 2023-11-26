package com.MP3.supplierservice.producer;

import com.MP3.supplierservice.event.ResupplyDeliveryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ResupplyDeliveryProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void produce(ResupplyDeliveryEvent event) {
        kafkaTemplate.send("resupplyDeliveryTopic", event);
    }
}
