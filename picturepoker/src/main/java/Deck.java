import java.util.ArrayList;
import java.util.List;

import static java.util.List.of;

public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = generateCards();
    }

    public List<Card> getCards() {
        return cards;
    }

    private List<Card> generateCards() {
        List<Card> cardList = new ArrayList<>();

        for(Suit suit : Suit.values()){
            for (Face face: Face.values()){
                cardList.add(new Card(suit, face));
            }
        }
        return cardList;
    }

    public Card drawCard() {
        if(cards.isEmpty()){
            throw new IllegalStateException();
        }

        int indexCardToDraw = (int) (Math.random() * (cards.size() - 1));

        Card cardDrawn = cards.get(indexCardToDraw);
        cards.remove(indexCardToDraw);

        return cardDrawn;
    }

    public Card swapCard(Card toSwapCard) {
        return swapCards(of(toSwapCard)).get(0);
    }

    public List<Card> swapCards(List<Card> toSwapCards) {
        if(toSwapCards.stream().anyMatch(c -> cards.contains(c)) || cards.isEmpty()){
            throw new IllegalArgumentException();
        }

        List<Card> newCards = new ArrayList<>();
        for(int i = 0; i < toSwapCards.size(); i++){
            newCards.add(drawCard());
        }

        cards.addAll(toSwapCards);

        return newCards;
    }
}
