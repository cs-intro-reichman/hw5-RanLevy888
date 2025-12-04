public class Wordle {

    // Reads all words from dictionary filename into a String array.
    public static String[] readDictionary(String filename) {
		In in = new In(filename);
        String[] dict = in.readAllLines();
        return dict;
    }

    // Choose a random secret word from the dictionary. 
    // Hint: Pick a random index between 0 and dict.length (not including) using Math.random()
    public static String chooseSecretWord(String[] dict) {
	  int choose = (int)Math.random() * dict.length;
      return dict[choose];
    }

    // Simple helper: check if letter c appears anywhere in secret (true), otherwise
    // return false.
    public static boolean containsChar(String secret, char c) {
        
	         for(int i = 0 ; i<5; i++){
                if (c == secret.charAt(i)) 
            return true;
             }
             return false;
    }

    // Compute feedback for a single guess into resultRow.
    // G for exact match, Y if letter appears anywhere else, _ otherwise.
    public static void computeFeedback(String secret, String guess, char[] resultRow) {
        int i;
        
		for( i =0 ; i<5 ; i++)
        {
            if(secret.charAt(i)==guess.charAt(i)){
             resultRow[i]='G';
            }
            else{
                for (int j = 0 ; j<5 ; j++){
                if(guess.charAt(i)==secret.charAt(j)){
                    resultRow[i]='Y';
                }
                }
                if(resultRow[i]!='G' && resultRow[i]!='Y')
                    resultRow[i]='_';
                
            }
            }
        }
        
    

    // Store guess string (chars) into the given row of guesses 2D array.
    // For example, of guess is HELLO, and row is 2, then after this function 
    // guesses should look like:
    // guesses[2][0] // 'H'
	// guesses[2][1] // 'E'
	// guesses[2][2] // 'L'
	// guesses[2][3] // 'L'
	// guesses[2][4] // 'O'
    public static void storeGuess(String guess, char[][] guesses, int row) {
		for(int i = 0 ; i<5 ; i++){
            guesses[row][i]=guess.charAt(i);
        }
    }

    // Prints the game board up to currentRow (inclusive).
    public static void printBoard(char[][] guesses, char[][] results, int currentRow) {
        System.out.println("Current board:");
        for (int row = 0; row <= currentRow; row++) {
            System.out.print("Guess " + (row + 1) + ": ");
            for (int col = 0; col < guesses[row].length; col++) {
                System.out.print(guesses[row][col]);
            }
            System.out.print("   Result: ");
            for (int col = 0; col < results[row].length; col++) {
                System.out.print(results[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Returns true if all entries in resultRow are 'G'.
    public static boolean isAllGreen(char[] resultRow) {
		for(int i = 0 ; i<5 ; i++){
            if(resultRow[i]!='G')
                return false;
        }
        
            return true;
        
    }

    public static void main(String[] args) {

    final int WORD_LENGTH = 5;
    final int MAX_ATTEMPTS = 6;

    // Read dictionary
    String[] dict = readDictionary("dictionary.txt");

    // Choose secret word
    String secret = chooseSecretWord(dict);

    // Prepare 2D arrays for guesses and results
    char[][] guesses = new char[MAX_ATTEMPTS][WORD_LENGTH];
    char[][] results = new char[MAX_ATTEMPTS][WORD_LENGTH];

    In inp = new In();

    int attempt = 0;
    boolean won = false;

    while (attempt < MAX_ATTEMPTS && !won) {

        String guess = "";
        boolean valid = false;

        // Read a valid guess
        while (!valid) {
            System.out.print("Enter your guess (5-letter word): ");
            guess = inp.readString().toUpperCase();

            if (guess.length() != WORD_LENGTH) {
                System.out.println("Invalid word. Please try again.");
            } else {
                valid = true;
            }
        }

        // Store guess in guesses array
         storeGuess(guess, guesses, attempt);
        // Compute feedback
        computeFeedback(secret, guess, results[attempt]);

        // Print board
        printBoard(guesses, results, attempt);

        // Check if all green
        if (isAllGreen(results[attempt])) {
            System.out.println("Congratulations! You guessed the word in " + (attempt + 1) + " attempts.");
            won = true;
        }

        attempt++;
    }

    // If lost
    if (!won) {
        System.out.println("Sorry, you did not guess the word.");
        System.out.println("The secret word was: " + secret);
    }

    inp.close();
}
    }

