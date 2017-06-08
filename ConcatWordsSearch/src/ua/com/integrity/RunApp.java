package ua.com.integrity;

import java.io.IOException;
import java.util.List;

public class RunApp {

	public static void main(String[] args) {
		
		Search sch = new Search("words.txt");
		
		int concatWordsCount;
		try {
			concatWordsCount = sch.createConcatWordsLists().size();
			List<String> theLongest = sch.getTheLongestWords();
			List<String> secondLongest = sch.getSecondLongestWords();
			
			System.out.println("Total count of concatenated words: " + concatWordsCount);
			System.out.println("The count of the longest concatenated words is " + theLongest.size());
			
			for(String word : theLongest) {
				System.out.println(word);
			}
			
			System.out.println("The count of the second longest concatenated words is " + secondLongest.size());
			
			for(String word : secondLongest) {
				System.out.println(word);
			}
		} catch (IOException e) {
			System.out.println("A problem occurred while reading file: " + e.getMessage());
		}
	}
}
