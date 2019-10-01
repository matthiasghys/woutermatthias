import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner reader =
                new Scanner(new InputStreamReader(System.in));
        System.out.println("geef de lengte van je veld op");
        int length = reader.nextInt();
        System.out.println("geef de breedte van je veld op");
        int width =reader.nextInt();
        System.out.println("geef het aantal mijnen op");
        int mines =reader.nextInt();


        Board board= new Board(length, width, mines);
        System.out.println(board.getFieldStatus());
        while(!(board.hasWon() || board.hasLost())){
            try {

                System.out.println("Geef je coördinaat in en of jewilt vlaggen");
                System.out.println("row?");
                int row =reader.nextInt();
                System.out.println("column?");
                int column = reader.nextInt();
                System.out.println("Vlaggen?");
                boolean flag = reader.nextBoolean();
                board.playCoordinate(row,column, flag);
                System.out.println(board.getFieldStatus());
            } catch (InputMismatchException e) {
                System.out.println("Foute input. Probeer opnieuw.");
            }

        }


    }
}
