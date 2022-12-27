import java.util.Random;

public class Mastermind {

    public static char[] answer;
    
    public static void main(String[] args) {
        // Create computer random answer
        setAnswerSequence();

        // Take guesses from user
        // Tell how many are correct
        // Show win when correct guess occurs
        // Show lose if cant guess in 10 guesses
        
    }

    private static void setAnswerSequence() {
        answer = new char[6];
        char[] colors = {'R', 'G', 'B', 'Y', 'O', 'P'};
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int x = random.nextInt(6);
            answer[i] = colors[x];
        }
    }

}
