package com.sentimentdetection.common;

/**
 * This class contains WordNet Entry along with its peripheries.
 * 
 * @author Kanchan Waikar
 * 
 *         Date Created : Mar 31, 2016 - 11:24:00 AM
 *
 */
public class WordNetSentiment {

	private String pos;
	private String wordId;
	private Double posScore;
	private Double negativeScore;
	private String synsetTerm;
	private String gloss;

	/**
	 * Field based constructor
	 * 
	 * @param pos
	 * @param wordId
	 * @param posScore
	 * @param negativeScore
	 * @param synsetTerm
	 * @param gloss
	 */
	public WordNetSentiment(String pos, String wordId, Double posScore, Double negativeScore, String synsetTerm,
			String gloss) {
		super();
		this.pos = pos;
		this.wordId = wordId;
		this.posScore = posScore;
		this.negativeScore = negativeScore;
		this.synsetTerm = synsetTerm;
		this.gloss = gloss;
	}

	/**
	 * @return the pos
	 */
	public String getPos() {
		return pos;
	}

	/**
	 * @return the wordId
	 */
	public String getWordId() {
		return wordId;
	}

	/**
	 * @return the posScore
	 */
	public Double getPosScore() {
		return posScore;
	}

	/**
	 * @return the negativeScore
	 */
	public Double getNegativeScore() {
		return negativeScore;
	}

	/**
	 * @return the synsetTerm
	 */
	public String getSynsetTerm() {
		return synsetTerm;
	}

	/**
	 * @return the gloss
	 */
	public String getGloss() {
		return gloss;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WordNetSentiment [pos=" + pos + ", wordId=" + wordId + ", posScore=" + posScore + ", negativeScore="
				+ negativeScore + ", synsetTerm=" + synsetTerm + ", gloss=" + gloss + "]";
	}

}
