package queue;

import helper.WordWithOrder;

import java.util.Comparator;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class QueueFinalConsumer extends Thread {
    private final Map<Integer, Boolean> finishedConsumers;
    private final Queue<WordWithOrder> inputQueue;
    private final Object syncObject;

    public QueueFinalConsumer(final Map<Integer, Boolean> finishedConsumers,
                              final Queue<WordWithOrder> inputQueue,
                              final Object syncObject) {
        this.finishedConsumers = finishedConsumers;
        this.inputQueue = inputQueue;
        this.syncObject = syncObject;
    }

    private boolean inputFinished() {
        if (finishedConsumers.isEmpty()) return false;
        return finishedConsumers.values().stream().allMatch(x -> x);
    }

    @Override
    public void run() {
        System.out.println("Final consumer. " + Thread.currentThread() + " - Commencing...");
        while (!inputFinished()) {
            System.out.println("Final consumer. " + Thread.currentThread() + " - Pending input. Awaiting...");
            try {
                synchronized (syncObject) {
                    syncObject.wait(500);
                }
                System.out.println("Final consumer. " + Thread.currentThread() + " - Received notification. Checking input.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Final consumer. " + Thread.currentThread() + " - Finished input. Printing result:");
        printCurrentResult();
    }

    public void printCurrentResult() {
        final String result = inputQueue.stream()
                .sorted(Comparator.comparingInt(WordWithOrder::getOrder))
                .map(WordWithOrder::getWord)
                .collect(Collectors.joining(" "));

        System.out.println("Current result:");
        System.out.println(result);
    }
}
