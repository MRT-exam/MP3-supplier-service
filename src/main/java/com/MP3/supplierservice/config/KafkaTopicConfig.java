package com.MP3.supplierservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;


    @Bean
    public NewTopic inventoryRestockTopic() {
        return TopicBuilder.name("inventoryRestockTopic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic inventoryDeliveryTopic() {
        return TopicBuilder.name("inventoryDeliveryTopic")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
