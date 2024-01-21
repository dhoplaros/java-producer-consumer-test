package singlethread;

import helper.WordWithOrder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FinalConsumer {
    public final List<WordWithOrder> input = new ArrayList<>();

    public void consume(final WordWithOrder word) {
        input.add(word);
    }

    public void printCurrentResult() {
        final String result = input.stream()
                .sorted(Comparator.comparingInt(WordWithOrder::getOrder))
                .map(WordWithOrder::getWord)
                .collect(Collectors.joining(" "));

        System.out.println("Current result:");
        System.out.println(result);
    }
}
