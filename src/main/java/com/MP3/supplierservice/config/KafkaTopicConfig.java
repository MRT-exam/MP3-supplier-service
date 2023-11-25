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

    // Consuming Topics
    @Bean
    public NewTopic inventoryResupplyTopic() {
        return TopicBuilder.name("inventoryResupplyTopic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    // Producing Topics
    @Bean
    public NewTopic resupplyDeliveryTopic() {
        return TopicBuilder.name("resupplyDeliveryTopic")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
