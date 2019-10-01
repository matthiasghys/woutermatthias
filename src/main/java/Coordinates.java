import java.util.Objects;

class Coordinates {


    private int x;
    private int y;
    private static final int MAX_SIZE = 9;
    private boolean isMine;
    private boolean isFlagged;
    private boolean isRevealed = false;

    private int neighbouringMines = 0;

    public Coordinates(int x, int y, boolean isMine) {
        this.isMine = isMine;
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("coordinates cannot be below 0");
        }
        if (x > MAX_SIZE || y > MAX_SIZE) {
            throw new IllegalArgumentException("size too big");
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void toggleFlag() {
        if (!isRevealed) {
            isFlagged = !isFlagged;
        }
    }

    public void reveal() {
        isRevealed = true;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine() {
        isMine = true;
    }

    public void setNeighbouringMines(int amount) {
        this.neighbouringMines = amount;
    }

    public int getNeighbouringMines() {
        return neighbouringMines;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y &&
                isMine == that.isMine &&
                isFlagged == that.isFlagged &&
                isRevealed == that.isRevealed &&
                neighbouringMines == that.neighbouringMines;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, isMine, isFlagged, isRevealed, neighbouringMines);
    }

    @Override
    public String toString() {
        if (isRevealed) {
            if (isMine) {
                return "*";
            } else {
                return String.valueOf(neighbouringMines);
            }
        } else if (isFlagged){
            return "!";
        } else {
            return ".";
        }
    }
}
