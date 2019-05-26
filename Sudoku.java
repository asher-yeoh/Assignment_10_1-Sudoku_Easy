/**
 * Sudoku
 */
import java.util.*;

public class Sudoku {

    static void displayBoard(String boardString) {
        String[] boardRows = {"", "", "", "", "", "", "", "", ""};
        char[] boardArray = boardString.toCharArray();
    
        for(int index = 0; index < boardString.length(); index ++ ){
            boardRows[index/9] = boardRows[index/9] + " " + boardArray[index];
            if(index % 9 == 2 || index % 9 == 5){
                boardRows[index/9] = boardRows[index/9] + " |";
            }
        }
        System.out.println("-------------------------");
        for(int index = 0; index < 9; index ++ ){
            System.out.println("|" + boardRows[index] + " |");
            if(index == 2 || index == 5){
                System.out.println("-------------------------");
            }
        }
        System.out.println("-------------------------");
    }

    static char[][] convertStringToBoard(String boardString) {
        int row = 9;
        int col = 9;

        char[][] board = new char[row][col];
    
        int offset = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = boardString.charAt(offset++);             
            }
        }
        return board;
    }

    static String convertBoardToString(char[][] board) {
        String boardString = "";

        for (int i = 0; i < board.length; i++) {
            boardString = boardString + String.copyValueOf(board[i]);
        }

        return boardString;
    }
    
    static String solve(String boardString){
        displayBoard(boardString);
        return boardString;
    }

    static ArrayList<Character> isInRow(ArrayList<Character> arrayListOfPossibleValues, char[][] board, int indexRow) {
        int col = 9;

        for (int i = 0; i < col; i++) {
            for(int value = 0; value < arrayListOfPossibleValues.size(); value++){
                if (arrayListOfPossibleValues.get(value) == board[indexRow][i]) {
                    arrayListOfPossibleValues.remove(value);
                }
            }			
        }

        return arrayListOfPossibleValues;
    }
    
    static ArrayList<Character> isInCol(ArrayList<Character> arrayListOfPossibleValues, char[][] board, int indexCol) {
		int row = 9;
		
		for (int i = 0; i < row; i++) {
            for(int value = 0; value < arrayListOfPossibleValues.size(); value++){
                if (arrayListOfPossibleValues.get(value) == board[i][indexCol]) {
                    arrayListOfPossibleValues.remove(value);
                }
            }
        }
				
        return arrayListOfPossibleValues;
	}

    static ArrayList<Character> isInBox(ArrayList<Character> arrayListOfPossibleValues, char[][] board, int indexRow, int indexCol) {
		int r = indexRow - indexRow % 3;
		int c = indexCol - indexCol % 3;

		for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                for(int value = 0; value < arrayListOfPossibleValues.size(); value++){
                    if (arrayListOfPossibleValues.get(value) == board[i][j]) {
                        arrayListOfPossibleValues.remove(value);
                    }
                }
            }
        }
        
        return arrayListOfPossibleValues;
    }


    static void checkEmptyCells(char[][] board) {
        boolean isThereEmptyCell = true;

        while (isThereEmptyCell) {
            outerloop:
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[0].length; col++) {
                    if (board[row][col] == '0') {
                        int indexRow = row;
                        int indexCol = col;                  

                        Character[] listOfPossibleValues = {'1','2','3','4','5','6','7','8','9'};
                        ArrayList<Character> arrayListOfPossibleValues = new ArrayList<Character>(Arrays.asList(listOfPossibleValues));

                        arrayListOfPossibleValues = isInRow(arrayListOfPossibleValues, board, indexRow);
                        arrayListOfPossibleValues = isInCol(arrayListOfPossibleValues, board, indexCol);
                        arrayListOfPossibleValues = isInBox(arrayListOfPossibleValues, board, indexRow, indexCol);

                        if (arrayListOfPossibleValues.size() == 1) {
                            board[indexRow][indexCol] = arrayListOfPossibleValues.get(0);
                            break outerloop;
                        }
                    }

                    if (row == 8 && col == 8) {
                        isThereEmptyCell = false;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        String unsolved = "105802000090076405200400819019007306762083090000061050007600030430020501600308900";
        String solved = "145892673893176425276435819519247386762583194384961752957614238438729561621358947";

        System.out.println("\n========== Sudoku ==========");
        displayBoard(unsolved);

        char[][] board = new char[9][9];
        board = convertStringToBoard(unsolved);

        checkEmptyCells(board);

        String boardString = convertBoardToString(board);

        System.out.println("\n========== Solution ==========");
        String solution = solve(boardString);
        System.out.println(solution.equals(solved));

    }
}