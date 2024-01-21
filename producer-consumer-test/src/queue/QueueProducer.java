package queue;

import helper.WordWithOrder;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class QueueProducer extends Thread {
    private final AtomicBoolean inputComplete;
    private final Queue<WordWithOrder> consumer1Input;
    private final Queue<WordWithOrder> consumer2Input;
    private final String inputParagraph;
    private final Object consumer1;
    private final Object consumer2;

    public QueueProducer(final AtomicBoolean inputComplete,
                         final Queue<WordWithOrder> consumer1Input,
                         final Queue<WordWithOrder> consumer2Input,
                         final String inputParagraph,
                         final Object consumer1,
                         final Object consumer2) {
        this.inputComplete = inputComplete;
        this.consumer1Input = consumer1Input;
        this.consumer2Input = consumer2Input;
        this.inputParagraph = inputParagraph;
        this.consumer1 = consumer1;
        this.consumer2 = consumer2;
    }

    @Override
    public void run() {
        System.out.println("Producer - Thread: " + Thread.currentThread() + ". Commencing...");
        int order = 0;
        // TODO handle special characters when splitting words
        for (final String word : inputParagraph.toLowerCase().split("\s")) {
            if (word.length() < 4) {
                consumer1Input.add(new WordWithOrder().setOrder(order).setWord(word));
                synchronized (consumer1) {
                    consumer1.notify();
                }
            } else {
                consumer2Input.add(new WordWithOrder().setOrder(order).setWord(word));
                synchronized (consumer2) {
                    consumer2.notify();
                }
            }
            order++;
        }
        System.out.println("Producer - Thread: " + Thread.currentThread() + ". Input complete. Notifying consumers...");

        inputComplete.set(true);
        synchronized (consumer1) {
            consumer1.notify();
        }
        synchronized (consumer2) {
            consumer2.notify();
        }
    }
}
