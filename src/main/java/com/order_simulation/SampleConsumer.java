package com.order_simulation;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;


import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;


class SampleConsumer {
    public static void main(String[] args) throws InterruptedException {
        consumer();
        
    }
    public static void consumer() throws InterruptedException {
         // create a logger
         final Logger LOG = Logger.getLogger("SampleConsumer");
         
        // create a properties to be used to configure the consumer
        Properties properties = new Properties();
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "sample consumer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        properties.put("schema.registry.url", "http://0.0.0.0:8081");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        // create a KafkaConsumer and pass in the properties 
        KafkaConsumer<Integer, OrderDetails> kafkaConsumer = new KafkaConsumer<>(properties);

        // subscribe to a list topic to be consumed
        kafkaConsumer.subscribe(Arrays.asList("order"));

        // poll and wait for message
        while (true) {
            ConsumerRecords<Integer, OrderDetails> records = kafkaConsumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<Integer, OrderDetails> record : records) {
                    LOG.info("Message recieved: " + record.key() + ", " +  record.value());
                }
            }
        }    
}

 