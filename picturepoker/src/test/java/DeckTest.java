import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class DeckTest {
    private final int DECK_SIZE = Suit.values().length * Face.values().length;

    @Test
    void deck_containsCards() {
        assertThat(new Deck().getCards().size() == DECK_SIZE).isTrue();
        assertThat(new Deck().getCards()).doesNotHaveDuplicates();
    }


    @Test
    void drawCard() {
        Deck deck = new Deck();
        List<Card> originalDeckState = new ArrayList<>(deck.getCards());
        Card drawnCard = deck.drawCard();

        assertThat(drawnCard).isIn(originalDeckState);
        assertThat(deck.getCards().size()).isEqualTo(DECK_SIZE - 1);
        assertThat(deck.getCards()).doesNotContain(drawnCard);
    }

    @Test
    void swapCard_swapCardInDeck() {
        Deck deck = new Deck();
        assertThatIllegalArgumentException().isThrownBy(() -> deck.swapCard(deck.getCards().get(0)));
    }

    @Test
    void swapCard_swapCardFromEmptyDeck() {
        Deck deck = new Deck();

        Card card = drawEntireDeck(deck);

        assertThatIllegalArgumentException().isThrownBy(() -> deck.swapCard(card));
    }

    @Test
    void drawCard_drawCardFromEmptyDeck() {
        Deck deck = new Deck();

        drawEntireDeck(deck);
        assertThatIllegalStateException().isThrownBy(deck::drawCard);
    }

    @Test
    void swapCard() {
        Deck deck = new Deck();

        Card drawnCard = deck.drawCard();
        Card swappedCard = deck.swapCard(drawnCard);

        assertThat(deck.getCards()).doesNotContain(swappedCard);
        assertThat(deck.getCards()).contains(drawnCard);

        swappedCard = deck.swapCard(swappedCard);
        assertThat(deck.getCards()).doesNotContain(swappedCard);
    }

    private Card drawEntireDeck(Deck deck) {
        Card card = null;
        while (deck.getCards().size() > 0) {
            card = deck.drawCard();
        }

        return card;
    }
}