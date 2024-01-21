import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyApp {
    /**
     * This method prints out the total number of duplicate words in the supplied paragraph
     *
     * @param paragraph
     */
    public void wordFrequency(final String paragraph) {
        if (Objects.isNull(paragraph)) {
            return;
        }

        final Map<String, Integer> wordCounts = new HashMap<>();
        // TODO remove special characters, punctuation marks etc
        for (final String word : paragraph.toLowerCase().split("\s")) {
            if (!wordCounts.containsKey(word)) {
                wordCounts.put(word, 0);
            }

            wordCounts.put(word, wordCounts.get(word) + 1);
        }

        final long duplicates = wordCounts.values().stream().filter(x -> x > 1).count();

        System.out.println("Number of duplicates: " + duplicates);
    }
}
