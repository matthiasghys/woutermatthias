public enum Suit {

    HEARTS("♥"), DIAMONDS("♦"), CLUBS("♣"), SPADES("♠");

    String icon;

    Suit(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return icon;
    }
}
