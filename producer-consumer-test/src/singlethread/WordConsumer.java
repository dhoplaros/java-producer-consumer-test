package singlethread;

import helper.WordWithOrder;

public class WordConsumer {
    private final int id;
    private final FinalConsumer finalConsumer;

    public WordConsumer(final int id, final FinalConsumer finalConsumer) {
        this.id = id;
        this.finalConsumer = finalConsumer;
    }

    public void consume(final WordWithOrder word) {
        finalConsumer.consume(new WordWithOrder().setWord(word.getWord() + id).setOrder(word.getOrder()));
    }
}
