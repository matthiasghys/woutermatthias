import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;


class CoordinatesTest {
    @Test
    void coordinates_illegalInput() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Coordinates(-2, 2, true));
        assertThatIllegalArgumentException().isThrownBy(() -> new Coordinates(2, -2, true));

        assertThatIllegalArgumentException().isThrownBy(() -> new Coordinates(10, 2, true));
        assertThatIllegalArgumentException().isThrownBy(() -> new Coordinates(2, 10, true));
    }

    @Test
    void coordinates_initialStatus() {
        assertThat(new Coordinates(1, 1, true).isMine()).isTrue();
    }

    @Test
    void coordinates_toggleFlag() {
        Coordinates coordinates = new Coordinates(1, 1, true);
        coordinates.toggleFlag();
        assertThat(coordinates.isFlagged()).isTrue();
        coordinates.toggleFlag();
        assertThat(coordinates.isFlagged()).isFalse();

        coordinates.reveal();
        coordinates.toggleFlag();
        assertThat(coordinates.isFlagged()).isFalse();
    }


    @Test
    void coordinates_click() {
        Coordinates coordinates = new Coordinates(1, 1, true);
        coordinates.reveal();
        assertThat(coordinates.isMine()).isTrue();

        coordinates = new Coordinates(1, 1, false);
        coordinates.reveal();
        assertThat(coordinates.isMine()).isFalse();
    }

    @Test
    void coordinates_equals() {
        assertThat(new Coordinates(0, 0, true)).isEqualTo(new Coordinates(0, 0, true));
        assertThat(new Coordinates(0, 1, true)).isEqualTo(new Coordinates(0, 1, true));
        assertThat(new Coordinates(0, 1, false)).isEqualTo(new Coordinates(0, 1, false));

        assertThat(new Coordinates(0, 0, true)).isNotEqualTo(new Coordinates(0, 0, false));
        assertThat(new Coordinates(0, 0, true)).isNotEqualTo(new Coordinates(0, 1, true));
        assertThat(new Coordinates(0, 0, true)).isNotEqualTo(new Coordinates(1, 0, true));
    }
}