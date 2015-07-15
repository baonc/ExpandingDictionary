package com.bigdata.vccorp.expanding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Class build a dictionary from file "VietSentiWordnet_ver1.0.txt"<br>
 * Each element in dictionary is a word with two its scores (positive score and negative score)
 * 
 * @created 15 / 7 / 2015
 * @author baonc
 * @github https://github.com/baonc/ExpandingDictionary
 */
public class Dictionary {
	private static final String INPUT_PATH = "src/main/resources/VietSentiWordnet_ver1.0.txt";
	
	private ArrayList<WordScores> dictionary;
	
	/**
	 * Constructor
	 */
	public Dictionary() {
		dictionary = new ArrayList<>();
	}
	
	/**
	 * Read data from INPUT_PATH and build a dictionary<br>
	 * Each element in the dictionary is a word with its scores: 
	 * <li> positive score
	 * <li> negative score
	 */
	public void buildDictionary() {
		try(InputStream in = Files.newInputStream(Paths.get(INPUT_PATH));
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
