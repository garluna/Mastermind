import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Mastermind {

    private static final Set<String> YES = new HashSet<>(Arrays.asList("y", "yes"));
    private static final Set<String> NO = new HashSet<>(Arrays.asList("n", "no"));

    public static Character[] characters = {'R', 'G', 'B', 'Y', 'O', 'P'}; 
    public static int characterLength = 6;
    public static int answerLength = 0;
    public static int numberOfGuesses = 0;

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
        System.out.println("For each round a set of characters must be guessed.");
        System.out.println("You can guess between 1 to 10 characters.");
        while (answerLength < 1 || answerLength > 10) {
            System.out.println("How many characters would you like to guess each time?");
            String length = scanner.nextLine();
            int lengthNum = 0;
            try {
                lengthNum = Integer.valueOf(length);
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number between 1 and 10.");
                continue;
            }
            if (lengthNum > 0 && lengthNum < 11) {
                answerLength = lengthNum;
            } else {
                System.out.println("Invalid length. The length must be between 1 to 10.");
            }
        }
        System.out.println("The length has been set to " + answerLength);

        System.out.println("The default set of characters consists of " + printCharacterSet() + " representing the colors of the original Mastermind game.");
        System.out.println("Would you like to change the set of characters? Enter Y or N");
        String changeSet = "";
        changeSet = scanner.nextLine();
        while (!YES.contains(changeSet.toLowerCase()) && !NO.contains(changeSet.toLowerCase())) {
            System.out.println("Invalid answer. Enter Y or N to indicate whether you would like to change the characters.");
            changeSet = scanner.nextLine();
        }

        if (YES.contains(changeSet.toLowerCase())) {
            System.out.println("Enter the characters you would like to use. You can enter between 1 to 20 characters.");
            String set = "";
            set = scanner.nextLine();
            while (set.length() < 1 || set.length() > 20) {
                set = scanner.nextLine();
                System.out.println("Invalid length. Enter between 1 to 20 characters.");
            }
            updateCharacterSet(set.toUpperCase());
        }
        System.out.println("The characters have been set to " + printCharacterSet());

        System.out.println("After each guess, you will be told how many characters are in the correct place and how many characters are in incorrect places.");

        while (numberOfGuesses < 1 || numberOfGuesses > 20) {
            System.out.println("How many times would you like to be able to guess? Enter a number between 1 and 20.");
            String numberInput = scanner.nextLine();
            int numberOfTimes = 0;
            try {
            numberOfTimes = Integer.valueOf(numberInput);
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number between 1 and 20.");
                continue;
            }
            if (numberOfTimes > 0 && numberOfTimes < 21) {
                numberOfGuesses = numberOfTimes;
            } else {
                System.out.println("Invalid length. The number must be between 1 and 20.");
            }
        }
        System.out.println("You will have " + numberOfGuesses + " chances to guess the correct answer.");
        System.out.println("Good luck!");
    }

    private static void updateCharacterSet(String requestedChars) {
        characters = new Character[requestedChars.length()];
        characterLength = requestedChars.length();
        if (allowedCharacters != null) {
            allowedCharacters.clear();
        } else {
            allowedCharacters = new HashSet<Character>(Arrays.asList(characters));
        }
        for (int i = 0; i < requestedChars.length(); i++) {
            if (allowedCharacters.contains(requestedChars.charAt(i))) {
                characterLength--;
            } else {
                characters[i] = requestedChars.charAt(i);
                allowedCharacters.add(requestedChars.charAt(i));
            }
        }
    }

    private static void setAnswerSequence() {
        answer = "";
        answerCount = new Hashtable<>();
        Random random = new Random();
        for (int i = 0; i < answerLength; i++) {
            int x = random.nextInt(characterLength);
            answer += characters[x];
            answerCount.put(characters[x], answerCount.getOrDefault(characters[x], 0) + 1);
        }
    }

    private static void playRound() {
        // Create computer random answer
        setAnswerSequence();

        for (int i = 0; i < numberOfGuesses; i++) {
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

        
        // Show lost is can't guess in number of guesses
        System.out.println("You're out of guesses. The correct answer was " + answer);
        System.out.println("Better luck next time!"); 
    }

    private static boolean invalidInput(String guess) {
        if (guess.length() != answerLength) {
            System.out.println("You've entered an invalid guess. Guesses must be "+ answerLength + " characters long");
            return true;
        }

        if (allowedCharacters == null) {
            allowedCharacters = new HashSet<Character>(Arrays.asList(characters));
        }

        for (int i = 0; i < guess.length(); i++) {
            if (!allowedCharacters.contains(guess.charAt(i))) {
                System.out.println("You've entered an invalid guess. Guesses can only consist of characters " + printCharacterSet());
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

        System.out.println("Number of characters in correct place: " + correctCharacterCorrectPlace);
        System.out.println("Number of characters in wrong place: " + correctCharacterWrongPlace);
    }

    private static String printCharacterSet() {
        return Arrays.toString(Arrays.copyOfRange(characters, 0, characterLength));
    }

}
