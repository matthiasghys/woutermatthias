import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class HandTest {
    Card card = new Card(Suit.HEARTS, Face.ACE);

    @Test
    void hand_getCards() {
        assertThat(new Hand().getCards().size()).isEqualTo(0);
    }

    @Test
    void hand_addCards() {
        Hand hand= new Hand();

        hand.addCard(card);

        assertThat(hand.getCards().size()).isEqualTo(1);
        assertThat(hand.getCards()).contains(card);
        assertThatIllegalArgumentException().isThrownBy(()->hand.addCard(card));
    }

    @Test
    void hand_removeCard() {
        Hand hand= new Hand();
        hand.addCard(card);
        hand.removeCard(card);
        assertThat(hand.getCards()).doesNotContain(card);
    }

    @Test
    void hand_removeNonexistantCard() {
        assertThatIllegalArgumentException().isThrownBy(()-> new Hand().removeCard(card));
    }

    @Test
    void getUnlockedCards() {
        Hand hand = new Hand();
        assertThat(hand.getUnlockedCards()).isEmpty();

        hand.addCard(card);
        assertThat(hand.getUnlockedCards()).contains(card);

        hand.toggleLock(card);
        assertThat(hand.getUnlockedCards()).doesNotContain(card);
        hand.toggleLock(card);
        assertThat(hand.getUnlockedCards()).contains(card);
    }
}