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
           
        // create a logger
         final Logger LOG = Logger.getLogger("EmailConsumer");
         
        // create a properties to be used to configure the consumer
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "email-consumer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "email-consumer-group");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        

        // create a KafkaConsumer and pass in the properties 
         // subscribe to a list topic to be consumed
        KafkaConsumer<String, OrderDetails> emailConsumer = new KafkaConsumer<>(properties);
        emailConsumer.subscribe(Arrays.asList("email"));

       
        // poll and wait for message
        while (true) {
            LOG.info("Waiting for message............");
                Thread.sleep(5000L);
            ConsumerRecords<String, OrderDetails> records = emailConsumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, OrderDetails> record : records) {
                    System.out.println("Sending email to " + record.value());
                }
            }
        }    
}

 
    
