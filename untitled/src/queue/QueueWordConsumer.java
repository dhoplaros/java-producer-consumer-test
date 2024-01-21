package queue;

import helper.WordWithOrder;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class QueueWordConsumer extends Thread {
    private final int id;
    private final Queue<WordWithOrder> inputQueue;
    private final Queue<WordWithOrder> outputQueue;
    private final AtomicBoolean inputComplete;
    private final Map<Integer, Boolean> finishedConsumers;
    private final Object finalConsumerSyncObject;
    private final Object producerSyncObject;

    public QueueWordConsumer(final int id,
                             final Queue<WordWithOrder> inputQueue,
                             final Queue<WordWithOrder> outputQueue,
                             final AtomicBoolean inputComplete,
                             final Map<Integer, Boolean> finishedConsumers,
                             final Object finalConsumerSyncObject,
                             final Object producerSyncObject) {
        this.id = id;
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        this.inputComplete = inputComplete;
        this.finishedConsumers = finishedConsumers;
        this.finalConsumerSyncObject = finalConsumerSyncObject;
        this.producerSyncObject = producerSyncObject;
        this.finishedConsumers.put(id, false);
    }

    private void processElements() {
        while (!inputQueue.isEmpty()) {
            processElement(inputQueue.remove());
        }
    }

    private void processElement(final WordWithOrder word) {
        outputQueue.add(new WordWithOrder().setWord(word.getWord() + id).setOrder(word.getOrder()));
    }

    @Override
    public void run() {
        System.out.println("Current consumer: " + id + " - Thread: " + Thread.currentThread() + " - Commencing...");
        do {
            System.out.println("Current consumer: " + id + " - Thread: " + Thread.currentThread() + " - Processing elements...");
            processElements();
            try {
                System.out.println("Current consumer: " + id + " - Thread: " + Thread.currentThread() + " - Awaiting additional input...");
                synchronized (producerSyncObject){
                    producerSyncObject.wait(500);
                }
                System.out.println("Current consumer: " + id + " - Thread: " + Thread.currentThread() + " - Received notification. Continuing processing...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (!inputComplete.get());

        processElements();
        finishedConsumers.put(id, true);
        synchronized (finalConsumerSyncObject){
            finalConsumerSyncObject.notify();
        }
        System.out.println("Current consumer: " + id + " - Thread: " + Thread.currentThread() + " - Finished.");
    }

}
