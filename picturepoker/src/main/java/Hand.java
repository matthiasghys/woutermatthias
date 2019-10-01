import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.stream.Collectors.toList;

public class Hand {
    Map<Card, Boolean> cards = new HashMap<>();


    public List<Card> getCards(){
        return new ArrayList<>(cards.keySet());
    }

    void addCard(Card card){
        if (cards.containsKey(card)) {
            throw new IllegalArgumentException();
        }

        cards.put(card, false);
    }

    void removeCard(Card card){
        if (!cards.containsKey(card)) {
            throw new IllegalArgumentException();
        }

        cards.remove(card);
    }

    void toggleLock(Card card){
        cards.replace(card, cards.get(card), !cards.get(card));
    }

    List<Card> getUnlockedCards(){
        return cards.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .map(Entry::getKey)
                .collect(toList());
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
