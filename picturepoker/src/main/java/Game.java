import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Deck deck = new Deck();
        Player player= new Player();

        for (int i = 0; i < 5; i++) {
            player.addCard(deck.drawCard());
        }
        System.out.println(player.printHand());

        Scanner sc= new Scanner(System.in);
        do{
            System.out.println("geef de kaarten die je bij wilt houden. De kaarten zijn genummerd van 1 tot 5");
            String toLock = sc.nextLine();

            for (String card : toLock.split(" ")) {
                player.lockCard(player.getHand().getCards().get(Integer.parseInt(card) -1));
            }

            System.out.println(player.printHand());
        } while(!hasPlayerConfirmed(sc));

        player.swapUnlockedCards(deck);
        System.out.println(player.printHand());
    }

    private static boolean hasPlayerConfirmed(Scanner scanner) {
        System.out.println("is dit correct? Y/N");
        return scanner.nextLine().equalsIgnoreCase("Y");
    }
}
