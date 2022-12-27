import java.util.Random;
import java.util.Scanner;

public class Mastermind {

    public static String answer;
    
    public static void main(String[] args) {
        intro();

        // Take guesses from user
        playRound();
    }

    private static void intro() {
        System.out.println("**********************************");
        System.out.println("***   Welcome to Mastermind!   ***");
        System.out.println("**********************************");
        System.out.println("Mastermind is a strategy game in which you will be able to play against the computer.");
        System.out.println("For each round a set of letters must be guessed consisting of R, B, G, Y, O, or P.");
        System.out.println("After each guess, you will be told how many colors are in the correct place and how many colors are in incorrect places.");
        System.out.println("You will have 10 chances to guess the correct answer.");
        System.out.println("Good luck!");
    }

    private static void setAnswerSequence() {
        answer = "";
        char[] colors = {'R', 'G', 'B', 'Y', 'O', 'P'};
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int x = random.nextInt(6);
            answer += colors[x];
        }
    }

    private static void playRound() {
        // Create computer random answer
        setAnswerSequence();

        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            System.out.println("Enter guess #" + (i + 1));
            String guess = scanner.nextLine();

            if (guess.equals(answer)) {
                // Show win when correct guess occurs
                System.out.println("Congrats! You guessed the answer - good job!");
                scanner.close();
                return;
            } else {
                // Tell how many are correct
            }
        }
        scanner.close();

        
        // Show lose if cant guess in 10 guesses
        System.out.println("You're out of guesses. The correct answer was " + answer);
        System.out.println("Better luck next time!"); 
    }

}
