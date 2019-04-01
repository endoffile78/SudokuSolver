package solver;

public class Main {
	
	public static void main(String[] args) {
        Sudoku sudoko = new Sudoku();
        
        System.out.println("Attempting to solve:");
        sudoko.printBoard();
        
        sudoko.solve();
        
        System.out.println("Solution:");
        sudoko.printBoard();
	}

}
