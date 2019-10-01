import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class BoardTest {

    @Test
    void boardTest_init() {
        assertThatIllegalArgumentException().isThrownBy(()->new Board(10,10));
        assertThat(new Board(2,2));
        assertThatIllegalArgumentException().isThrownBy(()-> new Board(-1,-3));
        assertThatIllegalArgumentException().isThrownBy(()-> new Board(2,-1));
    }


    @Test
    void boardTest_generateFieldsWithMines() {
        Board board= new Board(1,1, 1);
        assertThat(board.getCoordinates()).containsOnly(new Coordinates(0,0, true));
        board= new Board(5,5, 25);
        assertThat(board.getCoordinates().size()).isEqualTo(25);
        board = new Board(2,2,4);
        assertThat(board.getCoordinates()).containsExactlyInAnyOrder(new Coordinates(0,0, true), new Coordinates(0,1, true), new Coordinates(1,0, true), new Coordinates(1,1, true));
    }

    @Test
    void boardTest_RightAmountOfMinesOnBoard() {
        Board board= new Board(3,3, 0);
        assertThat(board.getCoordinates().stream().filter(Coordinates::isMine).count()).isEqualTo(0);
        board= new Board(5,5, 7);
        assertThat(board.getCoordinates().stream().filter(Coordinates::isMine).count()).isEqualTo(7);


    }

    @Test
    void boardTest_hasWon() {
        Board board = new Board(1,1, 1);
        assertThat(board.hasWon());
    }

    @Test
    void hasWon_1MineMultipleFields() {
        Board board = new Board(2, 2, 1);
        assertThat(board.hasWon()).isFalse();

        board.getCoordinates().stream().filter(Coordinates::isMine).forEach(Coordinates::toggleFlag);
        assertThat(board.hasWon()).isTrue();
    }

    @Test
    void hasWon_1MineEverythingFlagged() {
        Board board = new Board(2, 2, 1);
        assertThat(board.hasWon()).isFalse();

        board.getCoordinates().forEach(Coordinates::toggleFlag);
        assertThat(board.hasWon()).isFalse();
    }

    @Test
    void hasWon_4Mines4Fields() {
        Board board = new Board(2, 2, 4);
        assertThat(board.hasWon()).isTrue();
    }

    @Test
    void hasWon_1Mine4fields() {
        Board board = new Board(2, 2, 1);
        assertThat(board.hasWon()).isFalse();

        board.getCoordinates().stream().filter(Coordinates::isMine).forEach(Coordinates::toggleFlag);
        assertThat(board.hasWon()).isTrue();

        board = new Board(2, 2, 1);
        assertThat(board.hasWon()).isFalse();

        board.getCoordinates().stream().filter(c -> !c.isMine()).forEach(Coordinates::reveal);
        assertThat(board.hasWon()).isTrue();
    }

    @Test
    void hasLost() {
        Board board = new Board(1, 1, 1);
        assertThat(board.hasLost()).isFalse();
        board.getCoordinates().stream().filter(Coordinates::isMine).forEach(Coordinates::reveal);

        assertThat(board.hasLost()).isTrue();
    }

    @Test
    void findNeighbouringMines(){
        Board board= new Board(2,2, 2);
        Coordinates coordinates = board.getCoordinates().stream().filter(c -> !c.isMine()).findFirst().get();
        board.setCoordinatesAdjacentMines(coordinates);

        assertThat(coordinates.getNeighbouringMines()).isEqualTo(2);
    }

    @Test
    void playCoordinate_ValidWin() {
        Board board = new Board(2, 2, 3);
        assertThat(board.hasWon()).isFalse();

        Coordinates coordinates = board.getCoordinates().stream().filter(c -> !c.isMine()).findFirst().get();
        board.playCoordinate(coordinates.getX(), coordinates.getY(), false);

        assertThat(board.hasWon()).isTrue();
        System.out.println(board.toString());
    }


    @Test
    void playCoordinate_ValidWinFlags() {
        Board board = new Board(2, 2, 3);
        assertThat(board.hasWon()).isFalse();

        board.getCoordinates().stream().filter(Coordinates::isMine).forEach(c -> board.playCoordinate(c.getX(), c.getY(), true));

        assertThat(board.hasWon()).isTrue();
    }


    @Test
    void playCoordinate_ValidWinFlagsWithExtraFlags() {
        Board board = new Board(2, 2, 3);
        assertThat(board.hasWon()).isFalse();

        board.getCoordinates().stream().filter(c -> !c.isMine()).forEach(c -> board.playCoordinate(c.getX(), c.getY(), true));
        assertThat(board.hasWon()).isFalse();

        board.getCoordinates().stream().filter(Coordinates::isMine).forEach(c -> board.playCoordinate(c.getX(), c.getY(), true));
        assertThat(board.hasWon()).isFalse();

        board.getCoordinates().stream().filter(c -> !c.isMine()).forEach(c -> board.playCoordinate(c.getX(), c.getY(), true));
        assertThat(board.hasWon()).isTrue();
    }

    @Test
    void playCoordinate_hasLost() {
        Board board = new Board(2, 2, 3);
        board.getCoordinates().stream().filter(Coordinates::isMine).forEach(c -> board.playCoordinate(c.getX(), c.getY(), false));
        assertThat(board.hasLost()).isTrue();
    }
}