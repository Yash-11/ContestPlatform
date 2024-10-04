// package com.example.contestplatform.config;

// import org.apache.kafka.clients.admin.AdminClient;
// import org.apache.kafka.clients.admin.NewTopic;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.core.KafkaAdmin;

// import jakarta.annotation.PostConstruct;

// import java.util.Collections;

// @Configuration
// public class KafkaConfig {

//     @Value("${SPRING_KAFKA_BOOTSTRAP_SERVERS}")
//     private String SPRING_KAFKA_BOOTSTRAP_SERVERS;

//     @Bean
//     public KafkaAdmin kafkaAdmin() {
//         System.err.println("bbbbbb"+SPRING_KAFKA_BOOTSTRAP_SERVERS);
//         return new KafkaAdmin(Collections.singletonMap("bootstrap.servers", SPRING_KAFKA_BOOTSTRAP_SERVERS));
//     }

//     @Bean
//     public AdminClient adminClient() {
//         return AdminClient.create(kafkaAdmin().getConfigurationProperties());
//     }

//     @Bean
//     public String createTopic() {
//         String topicName = "demo-topic";
//         NewTopic topic = new NewTopic(topicName, 1, (short) 1); // 1 partition, replication factor 1

//         try {
//             adminClient().createTopics(Collections.singleton(topic));
//             System.out.println("Creating new topic");
//             return "Topic created: " + topicName;
//         } catch (Exception e) {
//             e.printStackTrace();
//             System.out.println("Creating new topic not");
//             return "Failed to create topic: " + topicName;
//         }
//     }

// }

