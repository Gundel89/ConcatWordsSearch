package ua.com.integrity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class reads words from specified file, counts total
 * amount of concatenated words, shows the longest and
 * the second longest words
 * 
 * @author быртр
 *
 */
public class Search {

	private String filePath;
	private List<String> concatWords;
	private List<String> theLongestWords;
	private List<String> secondLongestWords;
	
	/**
	 * Constructor accepts path to the file with
	 * words, that should be listed one word per line
	 * and not contain spaces.
	 * @param filePath path to the file
	 */
	public Search(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * Returns a list of the longest concatenated words
	 * that are in the specified file
	 * @return List of the longest words
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<String> getTheLongestWords() throws FileNotFoundException, IOException {
		if (theLongestWords == null) {
			createConcatWordsLists();
		}
		return theLongestWords;
	}
	
	/**
	 * Returns a list of the second longest concatenated words
	 * that are in the specified file
	 * @return List of the second longest concatenated words
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<String> getSecondLongestWords() throws FileNotFoundException, IOException {
		if (secondLongestWords == null) {
			createConcatWordsLists();
		}
		return secondLongestWords;
	}
	
	/**
	 * Creates lists with all concatenated words, the longest and
	 * the second longest words
	 * @return List with all concatenated words from the file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<String> createConcatWordsLists() throws FileNotFoundException, IOException {
		if (concatWords == null) {
			List<String> words = getWordsFromFile();
			concatWords = getConcatWords(words);
		}
		
		concatWords.sort((String s1, String s2) -> {
			return s2.length() - s1.length();
		});
		
		int biggestLength = concatWords.get(0).length();
		theLongestWords = new ArrayList<>();
		int i = 0;
		
		do {
			theLongestWords.add(concatWords.get(i++));
		} while(concatWords.get(i).length() == biggestLength);
		
		int secondBiggestLenth = concatWords.get(i).length();
		secondLongestWords = new ArrayList<>();
		
		do {
			secondLongestWords.add(concatWords.get(i++));
		} while(concatWords.get(i).length() == secondBiggestLenth);
		
		return concatWords;
	}

	private List<String> getWordsFromFile() throws FileNotFoundException, IOException {
		List<String> result = new ArrayList<>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String word = null;
			while((word = reader.readLine()) != null) {
				if (!word.isEmpty()) {
					result.add(word);
				}
			}
		}
		return result;
	}
	
	private List<String> getConcatWords(List<String> words) {
		Set<String> dictionary = new HashSet<>(words);
		Map<String, Boolean> wordsHolder = new HashMap<>();
		List<String> result = new ArrayList<>();
		
        for (String word: words) {
            if (isConcatWord(word, wordsHolder, dictionary)) {
            	result.add(word);
            }
        }
        return result;
	}
	
	/**
	 * Defines is the word is concatenated.
	 * @param word
	 * @param wordsHolder collects all words as keys and true/false
	 * if they are concat/not concat as values
	 * @param dictionary contains all words for comparing
	 * @return true if {@code word} is concatenated and false if not
	 */
    private boolean isConcatWord(String word, Map<String, Boolean> wordsHolder, Set<String> dictionary) {
        if (wordsHolder.containsKey(word)) {
            return wordsHolder.get(word);
        }
        for (int i = 1; i <= word.length(); i++) {
        	String pre = word.substring(0, i);
        	
        	if (dictionary.contains(pre)) {
        		String post = word.substring(i);
        		
        		if (dictionary.contains(post) || isConcatWord(post, wordsHolder, dictionary)) {
        			wordsHolder.put(word, true);
        			return true;
                }
            }
        }
        wordsHolder.put(word, false);
        return false;
    }
    
}
