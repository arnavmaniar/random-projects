import java.util.Random;
import java.util.Scanner;
public class startGame {
   static int score = 0;

    // For taking random number new room


    public static int exploreRandom() {

        Random rand = new Random(); // Making random object
        int ran = rand.nextInt(12); // Taking random number between 0 and 1
        return ran;
    }

// For pausing the game

    public static void pause() {
        Scanner s = new Scanner(System.in);
        s.next();
    }

// Statements to clear the screen

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void confir() {
        while (true) {
            System.out.println("1. Play again");
            System.out.println("2. End Game");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.println("You are a brave adventurer...");
                startGame();
                break;
            } else if (choice == 2) {
                System.out.println("Good game! Your game is ending.");
                System.out.println("Final score: " + score);
                System.out.println("GG!");
                pause();
                clear();
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
    }
    public static void startGame() {
        System.out.println("You find yourself in a mysterious place:");
        System.out.println("1. Explore the Dark Cave");
        System.out.println("2. Explore the Enchanted Forest");
        System.out.print("Enter Your Choice: ");

        Scanner scanner = new Scanner(System.in);
        int userChoice = scanner.nextInt();

        if (userChoice == 1) {
            exploreCave();
        } else if (userChoice == 2) {
            enterForest();
        } else {
            System.out.println("Invalid choice! Game Over.");
            System.out.println(score);
            clear();

        }
    }


    public static void exploreCave(){
        System.out.println("You find yourself inside a dark cave with water dripping from the ceiling. There is a chest in front of you. Do you want to open it?");
        System.out.println("1. Open Chest ");
        System.out.println("2. Leave ");
        System.out.print("Enter Your Choice: ");
        Scanner scanner = new Scanner(System.in);
        int userChoice = scanner.nextInt();

        if (userChoice == 1) {
            int ran = exploreRandom();
            if (ran == 0){
                System.out.println("Nice treasure! You found the skill to code! (very rare) +100 score.");
                score += 100;
                confir();
            }
            else{
                System.out.println("You were brutally mauled by a surprise dragon. You die.");
                confir();
            }
        } else if (userChoice == 2) {
            System.out.println("Welp, time to head out!");
            System.out.println(score);
            confir();

        } else {
            System.out.println("Invalid choice! Game over!");
            clear();

        }
    }
    public static void enterForest(){
        System.out.println("You find yourself in a mysterious place:");
        System.out.println("1. You see a mystical unicorn scavenging for berries. Do you want to fight it? (the answer is yes). ");
        System.out.println("2. Don't fight it and end this round. (if u click this u suck). ");
        System.out.print("Enter Your Choice: ");

        Scanner scanner = new Scanner(System.in);
        int userChoice = scanner.nextInt();

        if (userChoice == 1) {
            int ran = exploreRandom();
            if (ran == 0){
                System.out.println("SOMEHOW YOU KILLED THE DRAGON. Have you been going to the gym? What's your max bench?? +100 score. ");
                score += 100;
                confir();
            }
            else {
                System.out.println("You lost. Hit the gym maybe. You probably bench 5 lbs.");
                confir();;
            }
        } else if (userChoice == 2) {
            System.out.println("u suck. also you get no score.");
            System.out.println(score);
            confir();
        } else {
            System.out.println("Invalid choice! Game over!");
            clear();
        }

    }

    }
