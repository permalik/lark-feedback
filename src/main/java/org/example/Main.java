package org.example;

public class Main {

    public static void main(String[] args) {
        FeedbackStubConsumer consumer = new FeedbackStubConsumer(
            "feedback.stub"
        );

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    System.out.println("Shutting down..");
                    consumer.close();
                })
            );

        System.out.printf("Starting Feedback..");

        try {
            while (true) {
                String processedPrompt = consumer.consumeAndProcess();
                if (processedPrompt != null) {
                    System.out.println("Processed prompt: " + processedPrompt);
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException err) {
            System.err.println("Interrupted: " + err.getMessage());
        }
    }
}
