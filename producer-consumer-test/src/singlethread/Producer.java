package singlethread;

import helper.WordWithOrder;

public class Producer {
    private final WordConsumer wordConsumer1;
    private final WordConsumer wordConsumer2;

    public Producer(final WordConsumer wordConsumer1, final WordConsumer wordConsumer2) {
        this.wordConsumer1 = wordConsumer1;
        this.wordConsumer2 = wordConsumer2;
    }

    public void process(final String inputParagraph) {
        int order = 0;
        for (final String word : inputParagraph.toLowerCase().split("\s")) {
            if (word.length() < 4) {
                wordConsumer1.consume(new WordWithOrder().setOrder(order).setWord(word));
            } else {
                wordConsumer2.consume(new WordWithOrder().setOrder(order).setWord(word));
            }
            order++;
        }
    }
}
