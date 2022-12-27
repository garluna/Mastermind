import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Mastermind {

    public static final Character[] colors = {'R', 'G', 'B', 'Y', 'O', 'P'}; 

    public static int answerLength = 0;

    public static Scanner scanner;
    public static String answer;
    public static Hashtable<Character, Integer> answerCount;
    public static Set<Character> allowedCharacters;
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        intro();

        // Take guesses from user
        playRound();

        scanner.close();
    }

    private static void intro() {
        System.out.println("**********************************");
        System.out.println("***   Welcome to Mastermind!   ***");
        System.out.println("**********************************");
        System.out.println("Mastermind is a strategy game in which you will be able to play against the computer.");

        // Answer length
        System.out.println("For each round a set of letters must be guessed consisting of R, B, G, Y, O, or P.");
        System.out.println("You can guess between 1 to 10 letters.");
        while (answerLength < 1 || answerLength > 10) {
            System.out.println("How many letters would you like to guess each time?");
            String length = scanner.nextLine();
            int lengthNum = Integer.valueOf(length);
            if (lengthNum > 0 && lengthNum < 11) {
                answerLength = lengthNum;
            } else {
                System.out.println("Invalid length. The length must be between 1 to 10.");
            }
        }
        System.out.println("The length has been set to " + answerLength);

        System.out.println("After each guess, you will be told how many letters are in the correct place and how many letters are in incorrect places.");
        System.out.println("You will have 10 chances to guess the correct answer.");
        System.out.println("Good luck!");
    }

    private static void setAnswerSequence() {
        answer = "";
        answerCount = new Hashtable<>();
        Random random = new Random();
        for (int i = 0; i < answerLength; i++) {
            int x = random.nextInt(colors.length);
            answer += colors[x];
            answerCount.put(colors[x], answerCount.getOrDefault(colors[x], 0) + 1);
        }
    }

    private static void playRound() {
        // Create computer random answer
        setAnswerSequence();

        for (int i = 0; i < 10; i++) {
            System.out.println("Enter guess #" + (i + 1));
            String guess = scanner.nextLine();
            guess = guess.toUpperCase();

            if (invalidInput(guess)) {
                i--;
                continue;
            }

            if (guess.equals(answer)) {
                // Show win when correct guess occurs
                System.out.println("Congrats! You guessed the answer - good job!");
                return;
            } else {
                // Tell how many are correct
                checkAnswerCloseness(guess);
            }
        }

        
        // Show lose if can't guess in 10 guesses
        System.out.println("You're out of guesses. The correct answer was " + answer);
        System.out.println("Better luck next time!"); 
    }

    private static boolean invalidInput(String guess) {
        if (guess.length() != answerLength) {
            System.out.println("You've entered an invalid guess. Guesses must be "+ answerLength + " letters long");
            return true;
        }

        if (allowedCharacters == null) {
            allowedCharacters = new HashSet<Character>(Arrays.asList(colors));
        }

        for (int i = 0; i < guess.length(); i++) {
            if (!allowedCharacters.contains(guess.charAt(i))) {
                System.out.println("You've entered an invalid guess. Guesses can only consist of characters R, G, B, Y, O, or P.");
                return true;
            }
        }

        return false;
    }

    private static void checkAnswerCloseness(String guess) {
        Hashtable<Character, Integer> guessCount = new Hashtable<>();
        int correctCharacterCorrectPlace = 0;
        int correctCharacterWrongPlace = 0;

        for (int i = 0; i < guess.length(); i++) {
            Character c = guess.charAt(i);
            if (c == answer.charAt(i)) {
                correctCharacterCorrectPlace++;
            }
            guessCount.put(c, guessCount.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < answer.length(); i++) {
            Character c = answer.charAt(i);
            int countInAnswer = answerCount.get(c);
            int countInGuess = guessCount.getOrDefault(c, 0);

            if (countInGuess == 0 || countInAnswer == 0) {
                continue;
            }

            if (countInGuess > countInAnswer) {
                correctCharacterWrongPlace += countInAnswer;
            } else {
                correctCharacterWrongPlace += countInGuess;
            }

            guessCount.remove(c);
        }
        correctCharacterWrongPlace -= correctCharacterCorrectPlace;

        System.out.println("Number of colors in correct place: " + correctCharacterCorrectPlace);
        System.out.println("Number of colors in wrong place: " + correctCharacterWrongPlace);
    }

}
