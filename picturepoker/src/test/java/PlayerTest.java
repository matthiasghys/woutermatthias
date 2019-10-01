import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PlayerTest {
    private Player player;
    private final int PLAYER_INIT_SCORE = 10;

    @BeforeEach
    void init() {
        player = new Player();
    }

    @Test
    void bet_invalidInput() {
        assertThatIllegalArgumentException().isThrownBy(() -> player.betWithPoints(-3));
        assertThatIllegalArgumentException().isThrownBy(() -> player.betWithPoints(PLAYER_INIT_SCORE + 2));
    }

    @Test
    void bet_validInput() {
        player.betWithPoints(7);
        assertThat(player.getScore()).isEqualTo(PLAYER_INIT_SCORE - 7);
    }

    @Test
    void addScore_invalidInput() {
        assertThatIllegalArgumentException().isThrownBy(() -> player.addScore(-1));
    }

    @Test
    void addScore_validInput() {
        player.addScore(7);
        assertThat(player.getScore()).isEqualTo(PLAYER_INIT_SCORE + 7);
    }

    @Test
    void swapUnlockedCards() {
        Deck deck = new Deck();

        Hand initialHand = player.getHand();
        Card card = deck.drawCard();

        initialHand.addCard(card);

        player.swapUnlockedCards(deck);

        assertThat(deck.getCards()).contains(card);
        assertThat(initialHand.getCards()).doesNotContain(card);
        assertThat(initialHand.getUnlockedCards()).doesNotContain(card);

        assertThat(initialHand.getCards().size()).isNotEqualTo(0);
    }


    @Test
    void toggleLock() {
        Deck deck = new Deck();

        Hand initialHand = player.getHand();
        Card card = deck.drawCard();

        initialHand.addCard(card);

        player.lockCard(card);
        player.swapUnlockedCards(deck);

        assertThat(deck.getCards()).doesNotContain(card);
        assertThat(initialHand.getCards()).contains(card);
        assertThat(initialHand.getUnlockedCards()).doesNotContain(card);

        assertThat(initialHand.getCards().size()).isNotEqualTo(0);
    }
}