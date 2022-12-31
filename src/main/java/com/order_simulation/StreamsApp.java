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
        // create a properties object and pass in the configuraton on how our streams app behave
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-app");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        properties.put("schema.registry.url", "http://0.0.0.0:8081");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://0.0.0.0:8081");

        
        final Serde<OrderDetails> ValueSerde = new SpecificAvroSerde<>();
        ValueSerde.configure(serdeConfig, false);
       
        // create a new streambuilder object
        StreamsBuilder builder = new StreamsBuilder();

        //open the stream
        // KStream<Integer, OrderDetails> email = builder.stream("order", Consumed.with(Serdes.Integer(), ValueSerde));
                
        // email.peek((key, value) -> System.out.println(("incoming message, key: " + key + ", value: " + value)))
        //         .mapValues((key, value) -> value.getEmail().toString())
        //         .to("email", Produced.with(Serdes.Integer(), Serdes.String()));


        KStream<Integer, OrderDetails> analytics = builder.stream("order", Consumed.with(Serdes.Integer(), ValueSerde));
        
        analytics.peek((key, value) -> System.out.println("incomming" + value))
                    .filter((key, value) -> value == value.getEmail())
                    .peek((key, value) -> System.out.println("pro"+ value));
                    //  .to("analytics", Produced.with(Serdes.Integer(), ValueSerde));
              
                       

        //create a kafkastreams application, pass in the streamsbuilder.build and properties
        KafkaStreams streams = new KafkaStreams(builder.build(), properties);
        
        //start the kafkastreams application
        streams.start();


        
    }
    
}
