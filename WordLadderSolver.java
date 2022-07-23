import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;

public class WordLadderSolver {
	
	private String startWord;
	
	private String endWord;
	
	private ArrayList<String> wordList = new ArrayList<String>();
	
	private HashMap<String, ArrayList<String>> connections = new HashMap<String, ArrayList<String>>();
	
	public WordLadderSolver(String startWord, String endWord, String wordleDict) throws IOException {
		
		// set the start and end word for the puzzle
		this.startWord = startWord;
		this.endWord = endWord;
		
		Scanner reader = new Scanner(new FileReader(wordleDict));
		
		// Now we read in the words and make some databases
		
		while (reader.hasNext()){
			
			// take in the new word
			String nextWord = reader.nextLine();
			
			// no matter what, we add it to the wordList
			wordList.add(nextWord);
			
			// Now we need to figure out connections. This means two things:
			// 1) for all the words in connections that it is 1 apart from, add it to their arrayLists in the hashMap
			// 2) We need a new key in the hashMap with all the words that connect to this word
			
			ArrayList<String> connectingWords = new ArrayList<String>();
			
			for (String potentialConnection: connections.keySet()) {
				if (isOneDifferent(potentialConnection, nextWord)) {
					
					// Adding to the key of potentialConnection in the hashMap
					connections.get(potentialConnection).add(nextWord);
					
					// This is the arrayList we'll put in the hashMap at the key of nextWord
					connectingWords.add(potentialConnection);
					
				}
			}
			
			// Adding the new key to the hashMap
			connections.put(nextWord, connectingWords);
			
		}
	}
	
	
	// This function is really important for creating our connections database
	// It takes in input of two words and ouputs true if they are exactly one letter different and false otherwise
	public boolean isOneDifferent(String word1, String word2) {
		
		// Just some safety... probably shouldn't ever come up but hey why not
		if (word1.length() != word2.length()) {
			return false;
		}
		
		// Now to count the differences
		
		int differenceCounter = 0;
		
		for (int i = 0; i < word1.length(); i ++) {
			if (word1.charAt(i) != word2.charAt(i)) {
				differenceCounter ++;
				
				// This is purely here for efficiency
				if (differenceCounter > 1) {
					return false;
				}
				
			}
		}
		
		if (differenceCounter == 1) {
			return true;
		}
		return false;
		
	}
	
	// THE BIG METHOD!!!!
	// My first draft of this is just implementing breadth first search (cookie monster queue version) and we'll see what happens!
	
	public ArrayList<ArrayList<String>> getSolution(){
			
		ArrayList<ArrayList<String>> paths = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> solutions = new ArrayList<ArrayList<String>>();
		
		// To start, we begin with the starting word
		ArrayList<String> firstStep = new ArrayList<String>();
		firstStep.add(startWord);
		
		paths.add(firstStep);
		
		// So here's the big while loop:
		//  - add a next word to each path in the paths array
		//  - if that next word was the endWord then return the path
		// easy!
		while (true) {
			
			ArrayList<ArrayList<String>> newPaths = new ArrayList<ArrayList<String>>();
			
			for (ArrayList<String> path : paths) {
				
				String currentWord = path.get(path.size()-1);
				
				// Now we use our connections HashMap to get all the possible next steps 
				// But what's with the try/catch, you might ask?
				// Well, as it turns out, some words are lonely and have no connections!
				// So if we try to do a .contains() on an empty list it'll throw a nullPointer exception
				
				try {
					ArrayList<String> potentialNextSteps = connections.get(currentWord);
					
					// first we check if we made it to the end
					if (potentialNextSteps.contains(endWord)) {
						path.add(endWord);
						System.out.println(path);
						solutions.add(path);
					}
					
					// otherwise, we need to try every possible next step. 
					// I'm hoping this won't cause too much of an efficiency problem because the max path length is probably no more than 6
					// I mean the solution has to be somewhat close...
					for (String nextStep : potentialNextSteps) {
						
						// this is just to avoid weird errors with editing path. It's just O(n) and our big O is way worse than O(n) already
						ArrayList<String> newPath = (ArrayList<String>) path.clone();
						
						newPath.add(nextStep);
						
						newPaths.add(newPath);
						
						
					}
				}
				
				catch (Exception e){
				}
				
				
			}
			paths = newPaths;
			if (solutions.size() > 0) {
				return solutions;
			}
			
		}
			
	}
	


}