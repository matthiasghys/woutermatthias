import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

class Board {

    private List<Coordinates> coordinates;
    private int amountOfMines;
    private final int rows;
    private final int columns;

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    private static final int MAX_SIZE = 9;

    public Board(int rows, int columns) {
        this(rows, columns, 0);
    }

    public Board(int rows, int columns, int amountOfMines) {
        this.rows = rows;
        this.columns = columns;
        if (rows < 0 || rows > MAX_SIZE || columns < 0 || columns > MAX_SIZE) {
            throw new IllegalArgumentException();
        }

        this.amountOfMines = amountOfMines;
        coordinates = generateFields(rows, columns);
    }

    private List<Coordinates> generateFields(int rows, int columns) {
        List<Coordinates> generated = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                generated.add(new Coordinates(row, column, false));
            }
        }

        int minesAdded = 0;
        while (minesAdded < amountOfMines) {
            int row = (int) (Math.random() * rows);
            int column = (int) (Math.random() * columns);

            Optional<Coordinates> coordinate = generated.stream().filter(c -> c.equals(new Coordinates(row, column, false))).findFirst();

            if (coordinate.isPresent()) {
                coordinate.get().setMine();
                minesAdded++;
            }
        }

        return generated;
    }

    public void playCoordinate(int row, int column, boolean flag) {
        Optional<Coordinates> first = getSpecificCoordinates(row, column);
        if(first.isPresent()){
            if(flag){
                first.get().toggleFlag();
            }else{
                first.get().reveal();
                setCoordinatesAdjacentMines(first.get());
            }
        }
    }

    public String getFieldStatus() {
        String status = this.toString();

        if(hasWon()){
            status += ("\nProficiat!");
        }
        if(hasLost()){

            coordinates.forEach(Coordinates::reveal);
            coordinates.forEach(this::setCoordinatesAdjacentMines);
            status = this.toString();
            status += ("\n Helaas. Volgende keer beter.");
        }

        return status;
    }

    void setCoordinatesAdjacentMines(Coordinates coordinates){
        int amountOfNeighbouringMines;

        List<Coordinates> adjacentCoordinates = getAdjacentFields(coordinates);

        amountOfNeighbouringMines = (int) adjacentCoordinates.stream().filter(Coordinates::isMine).count();

        if (amountOfNeighbouringMines == 0) {
            revealAdjacentFields(adjacentCoordinates);
            adjacentCoordinates.forEach(this::setCoordinatesAdjacentMines);
        }

        coordinates.setNeighbouringMines(amountOfNeighbouringMines);
    }

    private List<Coordinates> getAdjacentFields(Coordinates coordinates) {
        List<Coordinates> adjacentCoordinates = new ArrayList<>();

        for (int row = coordinates.getX() - 1; row <= coordinates.getX() + 1; row++) {
            for (int col = coordinates.getY() -1; col <= coordinates.getY() + 1; col++) {
                getSpecificCoordinates(row, col).filter(Board::isNeededAdjacentField).ifPresent(adjacentCoordinates::add);
            }
        }

        return adjacentCoordinates;
    }

    private static boolean isNeededAdjacentField(Coordinates c) {
        return !c.isRevealed() || (c.isMine() && c.isRevealed());
    }

    private void revealAdjacentFields(List<Coordinates> coordinates){
        coordinates.forEach(Coordinates::reveal);
    }

    public boolean hasWon() {
        return (areAllNonRevealedFieldsMines() || areAllMinesFlagged()) && !areAnyNonMinesFlagged() ;
    }

    private boolean areAnyNonMinesFlagged() {
        return getCoordinates().stream().filter(c -> !c.isMine()).anyMatch(Coordinates::isFlagged);
    }

    private boolean areAllMinesFlagged() {
        return getCoordinates().stream().filter(Coordinates::isMine).allMatch(Coordinates::isFlagged);
    }

    private boolean areAllNonRevealedFieldsMines(){
        return getCoordinates().stream().filter(c-> !c.isRevealed()).allMatch(Coordinates::isMine);
    }

    public boolean hasLost(){
        return getCoordinates().stream().filter(Coordinates::isRevealed).anyMatch(Coordinates::isMine);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        output.append(" | ");
        IntStream.range(0, columns).forEach(col -> output.append(col).append("    "));
        output.append("\n");

        for (int row = 0; row < rows; row++) {
            output.append(row);
            output.append("| ");
            for (int column = 0; column < columns; column++) {

                getSpecificCoordinates(row, column).ifPresent(output::append);
                output.append("    ");
            }
            output.append("\n");
        }

        return output.toString();
    }

    private Optional<Coordinates> getSpecificCoordinates(int row, int column) {
        return getCoordinates().stream().filter(c -> c.getX() == row && c.getY() == column).findFirst();
    }
}
