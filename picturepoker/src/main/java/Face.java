public enum Face {
    ACE("A"),KING("K"),QUEEN("Q"),JACK("J");


    String value;

    Face(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
