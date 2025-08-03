package org.example;

public class Main {

    public static void main(String[] args) {
        FeedbackStubConsumer consumer = new FeedbackStubConsumer(
            "feedback.stub"
        );
        ResponseDeliveryProducer responseDeliveryProducer =
            new ResponseDeliveryProducer("response.delivery");
        FeedbackStubProducer feedbackStubProducer = new FeedbackStubProducer(
            "feedback.stub"
        );

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    System.out.println("Shutting down..");
                    consumer.close();
                    responseDeliveryProducer.close();
                    feedbackStubProducer.close();
                })
            );

        System.out.printf("Starting Feedback..");

        try {
            while (true) {
                String processedPrompt = consumer.consumeAndProcess();
                if (processedPrompt != null) {
                    responseDeliveryProducer.produce(
                        "new_responsedel",
                        processedPrompt
                    );
                    feedbackStubProducer.produce(
                        "new_feedback",
                        processedPrompt
                    );
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException err) {
            System.err.println("Interrupted: " + err.getMessage());
        }
    }
}
