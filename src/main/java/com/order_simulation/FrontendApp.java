package com.order_simulation;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

class FrontendApp {

 
    public static void main(String[] args) throws InterruptedException {
        
        // create a logger
        final Logger LOG = Logger.getLogger("BackendApp");
     
        // set the producer configuration
        Properties properties = new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "frontend producer");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName() );
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName() );
        properties.put("schema.registry.url", "http://0.0.0.0:8081");

        
        //create a list for items ordered
        ArrayList <CharSequence> items = new ArrayList<>();
        items.add("item_1");
        items.add("item_2");
        items.add("item_3");

        //create a kafka producer object
        KafkaProducer<Integer, OrderDetails> producer = new KafkaProducer<>(properties);

        // use a for each loop set the details and to send 100 messages to kafka
        for (int i = 0; i < 100; i++) {
            OrderDetails details = new OrderDetails();
            details.setEmail("user" + i +"@mail.com");
            details.setItemsordered(items);
            details.setOrderid(0011 + i);
            details.setUserid(1000 + i);
            details.setUsername("user_" + i);
            details.setTotalcost(100 + i * i);

            
            producer.send(new ProducerRecord("order", details.getUserid(), details));
            Thread.sleep(900L);
        
            LOG.info("Sending data to kafka");

        }
            
        producer.flush();         
        producer.close();
        LOG.info("Producer finished sending");            
                   
    }
    
}

