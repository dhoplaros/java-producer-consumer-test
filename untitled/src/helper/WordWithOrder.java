package helper;

public class WordWithOrder {
    private int order;
    private String word;

    public int getOrder() {
        return order;
    }

    public WordWithOrder setOrder(final int order) {
        this.order = order;
        return this;
    }

    public String getWord() {
        return word;
    }

    public WordWithOrder setWord(final String word) {
        this.word = word;
        return this;
    }
}
