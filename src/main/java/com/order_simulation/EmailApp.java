package com.order_simulation;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class EmailApp {
    public static void main(String[] args) throws InterruptedException {
           
        
        final Logger LOG = Logger.getLogger("EmailConsumer");
         
        // consumer configurations
        Properties configs = new Properties();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "email-consumer-group");
        configs.put(ConsumerConfig.CLIENT_ID_CONFIG, "email-consumer 01 ");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
 
        
        KafkaConsumer<String, OrderRecords> emailConsumer = new KafkaConsumer<>(configs);
        emailConsumer.subscribe(Arrays.asList("email"));

       
        // poll and wait for messages
        while (true) {
            ConsumerRecords<String, OrderRecords> records = emailConsumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, OrderRecords> record : records) {
                    if (record == null) {
                        LOG.info("Waiting for message............");
                        }
                    else
                        LOG.info("Sending email to " + record.value());
                }
            }
        }    
}

 
    
