package com.bigdata.vccorp.expanding;

/**
 * Scores of a word:<br>
 * <li> Positive score
 * <li> Negative score
 * 
 * @created 15 / 7 / 2015
 * @author baonc
 * @github https://github.com/baonc/ExpandingDictionary
 */
public class WordScores {
	private String word;
	private double positiveScore;
	private double negativeScore;
	
	/**
	 * Set word
	 * 
	 * @param word	: word
	 */
	public void setWord(String word) {
		this.word = word;
	}
	
	/**
	 * Set positive score
	 * 
	 * @param score	: positive score
	 */
	public void setPositiveScore(double score) {
		positiveScore = score;
	}
	
	/**
	 * Set negative score
	 * 
	 * @param score	: negative score
	 */
	public void setNegativeScore(double score) {
		negativeScore = score;
	}
	
	/**
	 * Get word
	 * 
	 * @return	: word
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * Get positive score
	 * 
	 * @return	: positive score
	 */
	public double getPositiveScore() {
		return positiveScore;
	}
	
	/**
	 * Get negative score
	 * 
	 * @return	: negative score
	 */
	public double getNegativeScore() {
		return negativeScore;
	}
}
