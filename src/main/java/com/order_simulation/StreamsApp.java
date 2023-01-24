package com.order_simulation;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;


public class StreamsApp {
    
    public static void main(String[] args) {
        // set streams app configurations
        Properties configs = new Properties();
        configs.put(StreamsConfig.APPLICATION_ID_CONFIG, "Streams App");
        configs.put(StreamsConfig.CLIENT_ID_CONFIG, "Streams App 1");
        configs.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        configs.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        configs.put("schema.registry.url", "http://0.0.0.0:8081");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://0.0.0.0:8081");

        
        final Serde<OrderRecords> ValueSerde = new SpecificAvroSerde<>();
        ValueSerde.configure(serdeConfig, false);
       
        StreamsBuilder builder = new StreamsBuilder();

        //create a kstream object
        KStream<Integer, OrderRecords> email = builder.stream("order", Consumed.with(Serdes.Integer(), ValueSerde));
        
        email.peek((key, value) -> System.out.println(("incoming message, key: " + key + ", value: " + value)))
                 .mapValues((key, value) -> value.getEmail())
                 .to("email", Produced.with(Serdes.Integer(), Serdes.String()));
                
        
         
        
        KafkaStreams streams = new KafkaStreams(builder.build(), configs);
        
        streams.start();
        // add shutdown hook
    }
}