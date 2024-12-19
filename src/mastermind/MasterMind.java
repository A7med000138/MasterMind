package mastermind;
import java.util.Scanner;
import java.util.Random;
import java.util.*;
public class MasterMind
{

    private static final int CODE_LENGTH = 4;  // Length of the code
    private static final int MAX_ATTEMPTS = 10; // Maximum attempts
    private static final String[] COLORS = {"R", "G", "B", "Y", "O", "P"}; // Available colors

    public static int getCODE_LENGTH()
    {
        return CODE_LENGTH;
    }

    public static int getMAX_ATTEMPTS() 
    {
        return MAX_ATTEMPTS;
    }

    public static String[] getCOLORS() 
    {
        return COLORS;
    }

    public static void main(String[] args) {
        String[] secretCode = generateSecretCode();
        System.out.println("Welcome to Mastermind!");
        System.out.println("Colors: R (Red), G (Green), B (Blue), Y (Yellow), O (Orange), P (Purple)");
        System.out.println("Guess the 4-color code. You have " + MAX_ATTEMPTS + " attempts.");

        boolean isSolved = false;
        Scanner scanner = new Scanner(System.in);

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++)
        {
            System.out.print("Attempt " + attempt + ": Enter your guess (e.g., RGBY): ");
            String guess = scanner.nextLine().trim().toUpperCase();

            // Validate input
            if (!isValidGuess(guess)) 
            {
                System.out.println("Invalid guess. Please use exactly " + CODE_LENGTH + " colors from the set: " 
                                   + String.join(", ", COLORS));
                continue; // Skip to next iteration
            }

            // Evaluate the guess
            int[] feedback = evaluateGuess(secretCode, guess.split(""));
            System.out.println("Feedback: " + feedback[0] + " correct positions, " + feedback[1] + " correct colors.");

            // Check for victory
            if (feedback[0] == CODE_LENGTH)
            {
                isSolved = true;
                System.out.println("Congratulations! You've cracked the code!");
                break;
            }
        }

        // Game Over
        if (!isSolved) 
        {
            System.out.print("Game Over! The correct code was: ");
            System.out.println(String.join(" ", secretCode));
        }

        scanner.close();
    }

    // Generate a random secret code
    private static String[] generateSecretCode() 
    {
        Random random = new Random();
        String[] code = new String[CODE_LENGTH];
        for (int i = 0; i < CODE_LENGTH; i++) 
        {
            code[i] = COLORS[random.nextInt(COLORS.length)];
        }
        return code;
    }

    // Check if the guess is valid
    private static boolean isValidGuess(String guess)
    {
        if (guess == null || guess.length() != CODE_LENGTH) 
        {
            return false;
        }
        for (char c : guess.toCharArray()) 
        {
            if (!Arrays.asList(COLORS).contains(String.valueOf(c))) 
            {
                return false;
            }
        }
        return true;
    }

    // Evaluate the guess and return feedback
    private static int[] evaluateGuess(String[] secretCode, String[] guess)
    {
        int correctPosition = 0;
        int correctColor = 0;

        boolean[] codeUsed = new boolean[CODE_LENGTH];
        boolean[] guessUsed = new boolean[CODE_LENGTH];

        // Check for correct positions
        for (int i = 0; i < CODE_LENGTH; i++) 
        {
            if (secretCode[i].equals(guess[i]))
            {
                correctPosition++;
                codeUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        // Check for correct colors in wrong positions
        for (int i = 0; i < CODE_LENGTH; i++)
        {
            if (!guessUsed[i]) {
                for (int j = 0; j < CODE_LENGTH; j++)
                {
                    if (!codeUsed[j] && guess[i].equals(secretCode[j]))
                    {
                        correctColor++;
                        codeUsed[j] = true;
                        break;
                    }
                }
            }
        }

        return new int[]{correctPosition, correctColor};
    }
}