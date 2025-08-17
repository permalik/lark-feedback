package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        FeedbackStubConsumer consumer = new FeedbackStubConsumer(
            "feedback.stub"
        );

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    logger.info("Shutting down..");
                    consumer.close();
                })
            );

        logger.info("Starting Feedback..");

        try {
            while (true) {
                String processedPrompt = consumer.consumeAndProcess();
                if (processedPrompt != null) {
                    logger.info("Processed prompt: " + processedPrompt);
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException err) {
            logger.error("Interrupted: " + err.getMessage());
        }
    }
}
