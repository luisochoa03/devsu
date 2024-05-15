package org.banco.cuentas.config;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaTopicCreator {

    @PostConstruct
    public void createTopic() {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.119:9092");

        try (AdminClient admin = AdminClient.create(config)) {
            ListTopicsResult listTopics = admin.listTopics();
            Set<String> topics = listTopics.names().get();

            // Lista de topics a crear
            String[] topicsToCreate = {"generarEECCById", "recibir-movimientos"};

            for (String topicToCreate : topicsToCreate) {
                if (!topics.contains(topicToCreate)) {
                    NewTopic newTopic = new NewTopic(topicToCreate, 1, (short) 1);
                    admin.createTopics(List.of(newTopic));
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}