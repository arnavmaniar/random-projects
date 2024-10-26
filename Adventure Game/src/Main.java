import java.util.Scanner;
import java.util.Random;

public class Main {
    public static int health = 100; // Variable to calculate health
    public static int score = 0; // Variable to calculate score

    public static void main(String[] args) {
        System.out.println("Welcome To The Enchanted Forest Adventure Game!"); // printing the heading
        System.out.println();
        System.out.println(); // Adding space
        startGame game = new startGame();
        game.startGame(); // Calling startGame()
        game.clear(); // Calling clear()

    }
}

