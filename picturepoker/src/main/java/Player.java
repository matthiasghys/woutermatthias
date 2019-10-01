import java.util.List;

public class Player {
    private int score=10;
    private Hand hand = new Hand();

    void betWithPoints(int amount){
        if ( score < amount || amount < 0) {
            throw new IllegalArgumentException();
        }

        score -= amount;
    }

    void addScore(int amount){
        if (amount < 0) {
            throw new IllegalArgumentException();
        }

        score += amount;
    }


    public int getScore() {
        return score;
    }

    public Hand getHand() {
        return hand;
    }

    public void lockCard(Card card) {
        hand.toggleLock(card);
    }

    public void addCard(Card card){
        hand.addCard(card);
    }


    public void swapUnlockedCards(Deck deck){
        List<Card> newCards = deck.swapCards(hand.getUnlockedCards());


        hand.getUnlockedCards().forEach(c-> hand.removeCard(c));
        newCards.forEach(c -> hand.addCard(c));
    }

    public String printHand(){
        return hand.toString();
    }

}
