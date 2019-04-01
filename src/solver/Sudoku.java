package solver;

import java.util.ArrayList;
import java.util.Arrays;

class Sudoku {

    int board[][] =  {
        { 0, 4, 0, 0, 0, 0, 0, 1, 0 },
        { 5, 0, 2, 8, 0, 1, 0, 0, 0 },
        { 0, 7, 0, 2, 0, 0, 0, 0, 8 },
        { 0, 0, 0, 0, 1, 2, 0, 0, 3 },
        { 0, 8, 0, 0, 9, 0, 4, 0, 1 },
        { 0, 0, 1, 4, 0, 3, 0, 7, 0 },
        { 0, 3, 0, 9, 0, 0, 0, 0, 0 },
        { 8, 1, 7, 3, 0, 5, 0, 0, 0 },
        { 6, 2, 9, 1, 0, 0, 5, 3, 7 },
    };
    
    /*
     * Prints the sudoku board out
     */
    public void printBoard(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /*
     * Create an ArrayList of all possible values
     */
    private ArrayList<Integer> getPossibleValues(){
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        numbers.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)); //add all possible values to the ArrayList
        return numbers;
    }
    
    /*
     * Remove numbers from possible choices that have already been used
     */
    private void pruneBadValues(ArrayList<Integer> numbers, int row, int column){
        if (row >= 9 || column >= 9) { //check that the row and column are valid
            return;
        }
        
        for (int i = 0; i < 9; i++) { //check for numbers that are already in rows and columns
            final int row_value = board[row][i];
            final int col_value = board[i][column];
            
            if (numbers.contains(row_value)) {
                numbers.remove((Integer)row_value); //remove numbers that are in the row already
            }
            
            if (numbers.contains(col_value)) {
                numbers.remove((Integer)col_value); //remove numbers that are in the column already
            }
        }
        
        for (int i = 0; i < numbers.size(); i++) { //check for numbers that are already in the 3x3 grid
        	final int value = numbers.get(i);
        	if (!checkSubGrid(row, column, value)) {
        		numbers.remove((Integer)value); //remove numbers that are in the 3x3 grid
        	}
        }
    }
    
    /*
     * Checks to make sure that a number is not repeated in the 3x3 grid
     */
    private boolean checkSubGrid(int row, int column, int number) {
    	if (row >= 9 || column >= 9) { //check that the column and row are valid
    		return false;
    	}
    	
    	//determine the beginning of the row
    	int rowStart = 0, rowEnd = 0;
    	if (row <= 2) {
    		rowStart = 0;
    	} else if (row >= 2 && row <= 5) {
    		rowStart = 3;
    	} else {
    		rowStart = 6;
    	}
    	
    	rowEnd = rowStart + 3; //row ends 3 away from the start
    	
    	//determine the beginning of the column
    	int colStart = 0, colEnd = 0;
    	if (column <= 2) {
    		colStart = 0;
    	} else if (column >= 2 && column <= 5) {
    		colStart = 3;
    	} else {
    		colStart = 6;
    	}
    	
    	colEnd = colStart + 3; //column ends 3 away from the start
    	
    	for (int i = rowStart; i < rowEnd; i++) {
    		for (int j = colStart; j < colEnd; j++) {
    			if (board[i][j] ==  number) { //number exists in 3x3 grid therefore bad placement
    				return false;
    			}
    		}
    	}
    	
    	
    	return true;
    }
    
    /*
     * Get the next empty cell on the board
     */
    private int[] getEmptyCell() {
    	for (int i = 0; i < 9; i++) { //iterate through the board
    		for (int j = 0; j < 9; j++) {
    			if (board[i][j] == 0) { //check to see if the cell is empty
    				return new int[] {i, j}; //return coords
    			}
    		}
    	}
    	
    	return null; //return null there is no empty cell left
    }
    
    /*
     * Solve the sudoku board using depth first search
     */
    public boolean solve(){
    	int coords[] = getEmptyCell(); //get the position of the next empty cell
    	if (coords == null) { //no more empty cells sudoku is solved
    		return true;
    	}
    	
    	int x = coords[0], y = coords[1];

		ArrayList<Integer> numbers = getPossibleValues();
		pruneBadValues(numbers, x, y); //get rid of invalid values
    	
    	for (int i = 0; i < numbers.size(); i++) { //iterate through the list of possible values
    		final int number = numbers.get(i);
        	board[x][y] = number; //set the number
        	if (solve()) { //check if the sudoku has been solved
        		return true; 
        	}
        		
        	board[x][y] = 0; //reset the cell
    	}
        
        return false; //sudoku hasn't been solved
    }
}