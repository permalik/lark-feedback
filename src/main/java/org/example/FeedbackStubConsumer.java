package org.example;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeedbackStubConsumer {

    private static final Logger logger = LoggerFactory.getLogger(
        FeedbackStubConsumer.class
    );
    private final KafkaConsumer<String, String> consumer;

    public FeedbackStubConsumer(String topic) {
        logger.info("Initializing consumer..");
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "lark-feedback");
        props.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class.getName()
        );
        props.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class.getName()
        );

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
        logger.info("Subscribed to topic");
    }

    public String consumeAndProcess() {
        ConsumerRecords<String, String> records = consumer.poll(
            Duration.ofMillis(2)
        );
        for (ConsumerRecord<String, String> record : records) {
            logger.info(
                "Consumed: key={}, value={}, offset={}",
                record.key(),
                record.value(),
                record.offset()
            );
            return record.value();
        }
        return null;
    }

    public void close() {
        consumer.close();
    }
}
