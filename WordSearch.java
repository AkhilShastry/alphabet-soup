import java.util.*;

public class WordSearch {

    // Define movement directions: eight possible directions 
    private static final int[] deltaRow = {-1, -1, -1, 0, 1, 1, 1, 0};
    private static final int[] deltaCol = {-1, 0, 1, 1, 1, 0, -1, -1};

    public static void main(String[] args) {
        // Sample input data for the word search
        String inputData = "5x5\n" +
                           "H A S D F\n" +
                           "G E Y B H\n" +
                           "J K L Z X\n" +
                           "C V B L N\n" +
                           "G O O D O\n" + 
                           "HELLO\n" +
                           "GOOD\n" +
                           "BYE\n";

        // Initialize the scanner to read the input data
        Scanner inputScanner = new Scanner(inputData);
        WordSearch wordSearch = new WordSearch();

        // Read the grid size and parse the dimensions
        String sizeLine = inputScanner.nextLine();
        String[] dimensions = sizeLine.split("x");
        int totalRows = Integer.parseInt(dimensions[0]);
        int totalCols = Integer.parseInt(dimensions[1]);

        // Populate the character grid with letters from the input
        char[][] grid = wordSearch.populateGrid(inputScanner, totalRows, totalCols);
        // Retrieve the list of words to search for from the input
        List<String> wordsToFind = wordSearch.retrieveWords(inputScanner);

        // Begin searching for the specified words in the grid
        wordSearch.searchForWords(grid, totalRows, totalCols, wordsToFind);
    }

    // This method creates the grid from the input, storing characters in a 2D array
    private char[][] populateGrid(Scanner scanner, int rows, int cols) {
        char[][] charGrid = new char[rows][cols];
        // Read each row and fill the grid
        for (int i = 0; i < rows; i++) {
            String[] characters = scanner.nextLine().trim().split(" ");
            for (int j = 0; j < cols; j++) {
                charGrid[i][j] = characters[j].charAt(0);
            }
        }
        return charGrid;
    }

    // This method retrieves the list of words to search for from the scanner
    private List<String> retrieveWords(Scanner scanner) {
        List<String> wordList = new ArrayList<>();
        // Read until there are no more lines
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine().trim();
            if (!word.isEmpty()) {
                // Only add non-empty words to the list
                wordList.add(word);
            }
        }
        return wordList;
    }

    // This method goes through each word and attempts to locate it in the grid
    private void searchForWords(char[][] grid, int rows, int cols, List<String> words) {
        for (String word : words) {
            locateWordInGrid(grid, rows, cols, word);
        }
    }

    // This method tries to find the specified word starting from each grid position
    private void locateWordInGrid(char[][] grid, int rows, int cols, String word) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Check if the first character matches
                if (grid[row][col] == word.charAt(0)) {
                    // Check all eight directions for the word
                    for (int direction = 0; direction < 8; direction++) {
                        if (isWordPresentInDirection(grid, rows, cols, word, row, col, deltaRow[direction], deltaCol[direction])) {
                            // Calculate the end position of the word
                            int endRow = row + (word.length() - 1) * deltaRow[direction];
                            int endCol = col + (word.length() - 1) * deltaCol[direction];
                            // Print the result in the desired format
                            System.out.println(word + " " + row + ":" + col + " " + endRow + ":" + endCol);
                            return; // Exit after finding the word
                        }
                    }
                }
            }
        }
    }

    // This method checks if the word can be found in a specific direction
    private boolean isWordPresentInDirection(char[][] grid, int rows, int cols, String word, int startRow, int startCol, int rowStep, int colStep) {
        int wordLength = word.length();
        int finalRow = startRow + (wordLength - 1) * rowStep;
        int finalCol = startCol + (wordLength - 1) * colStep;

        // Check if the final position is out of bounds
        if (finalRow < 0 || finalRow >= rows || finalCol < 0 || finalCol >= cols) {
            return false;
        }

        // Verify each character matches the word
        for (int i = 0; i < wordLength; i++) {
            int checkRow = startRow + i * rowStep;
            int checkCol = startCol + i * colStep;
            if (grid[checkRow][checkCol] != word.charAt(i)) {
                return false; // Mismatch found
            }
        }
        return true; // All characters match
    }
}
