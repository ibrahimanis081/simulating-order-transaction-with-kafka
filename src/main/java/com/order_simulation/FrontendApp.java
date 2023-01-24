package com.order_simulation;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;


import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

class FrontendApp {

 
    public static void main(String[] args) throws InterruptedException {
        
        
        final Logger LOG = Logger.getLogger("FrontEndApp");
     
        // set the producer configuration
        Properties configs = new Properties();
        configs.put(ProducerConfig.CLIENT_ID_CONFIG, "frontend producer");
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        configs.put(AbstractKafkaAvroSerDeConfig.AUTO_REGISTER_SCHEMAS, false);
        configs.put("schema.registry.url", "http://0.0.0.0:8081");

        
       
        
        ArrayList <String> items = new ArrayList<>();
        items.add("item_1");
        items.add("item_2");
        items.add("item_3");

        //create a kafka producer object
        KafkaProducer<Integer, OrderRecords> producer = new KafkaProducer<>(configs);

        // use a for each loop set the records and to send 100 messages to kafka
        for (int i = 1; i < 100; i++) {
            OrderRecords records = new OrderRecords();
            records.setUserID(i);
            records.setTransactionID(100_123_32 + i);
            records.setEmail("user" + i +"@mail.com");
            records.setItemsordered(items);
            records.setTotalcost(100 + i * i);

            
            producer.send(new ProducerRecord("order", records.getUserID(), records));
            Thread.sleep(900L);
        
            LOG.info("Sending data to kafka");

        }
            
        producer.flush();         
        producer.close();
        LOG.info("Producer finished sending");            
                   
    }
    
}

