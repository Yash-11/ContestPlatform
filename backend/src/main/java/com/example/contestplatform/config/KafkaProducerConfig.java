package com.example.contestplatform.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.contestplatform.model.Submission;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${SPRING_KAFKA_BOOTSTRAP_SERVERS}")
    private String SPRING_KAFKA_BOOTSTRAP_SERVERS;

    @Bean
    public ProducerFactory<String, Submission> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        System.err.println("bbbbbb"+SPRING_KAFKA_BOOTSTRAP_SERVERS);
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SPRING_KAFKA_BOOTSTRAP_SERVERS);
        // configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Use JsonSerializer here
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Submission> kafkaTemplate() {
        System.err.println("jjjjjjj");
        return new KafkaTemplate<>(producerFactory());
    }
}

