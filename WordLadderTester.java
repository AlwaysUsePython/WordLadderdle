import java.io.IOException;
import java.util.*;

public class WordLadderTester {
	
	public static void main (String [] args) throws IOException{
		
		// Taking input of the puzzle
		
		Scanner kbd = new Scanner(System.in);
		
		System.out.println("What's your starting word? ");
		
		String startWord = kbd.next();
		
		System.out.println("What's your ending word? ");
		
		String endWord = kbd.next();
		
		
		
		// Now we're going to call the methods in our solver class
		
		WordLadderSolver solver = new WordLadderSolver(startWord, endWord, "Weaver Dictionary");
		
		
		// This ArrayList will be a list of all the steps of the solution in order. 
		ArrayList<ArrayList<String>> solutionPaths = solver.getSolution();
		
		
		// Now we just display the results!
		System.out.println("HERE IS THE SOLUTION!!");
		for (ArrayList<String> path : solutionPaths) {
			
			for (String word : path) {
				System.out.println(word);
			}
			
			System.out.println();
		}
		
		
		
	}

}
