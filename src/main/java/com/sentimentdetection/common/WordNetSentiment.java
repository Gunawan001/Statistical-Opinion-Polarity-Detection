package com.sentimentdetection.common;

/**
 * This class contains WordNet Entry along with its peripheries.
 * 
 * @author Kanchan Waikar Date Created : Mar 31, 2016 - 11:24:00 AM
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
	 * field based constructor
	 * @param pos
	 * @param wordId
	 * @param posScore
	 * @param negativeScore
	 * @param synsetTerm
	 * @param gloss
	 */
	public WordNetSentiment(String pos, String wordId, double posScore, double negativeScore, String synsetTerm,
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
	 * @param pos the pos to set
	 */
	public void setPos(String pos) {
		this.pos = pos;
	}

	/**
	 * @return the gloss
	 */
	public String getGloss() {
		return gloss;
	}

	/**
	 * @param gloss the gloss to set
	 */
	public void setGloss(String gloss) {
		this.gloss = gloss;
	}

	/**
	 * @return the synsetTerm
	 */
	public String getSynsetTerm() {
		return synsetTerm;
	}

	/**
	 * @param synsetTerm
	 *            the synsetTerm to set
	 */
	public void setSynsetTerm(String synsetTerm) {
		this.synsetTerm = synsetTerm;
	}

	/**
	 * @return the wordId
	 */
	public String getWordId() {
		return wordId;
	}

	/**
	 * @param wordId
	 *            the wordId to set
	 */
	public void setWordId(String wordId) {
		this.wordId = wordId;
	}

	/**
	 * @return the posScore
	 */
	public double getPosScore() {
		return posScore;
	}

	/**
	 * @param posScore
	 *            the posScore to set
	 */
	public void setPosScore(double posScore) {
		this.posScore = posScore;
	}

	/**
	 * @return the negativeScore
	 */
	public double getNegativeScore() {
		return negativeScore;
	}

	/**
	 * @param negativeScore
	 *            the negativeScore to set
	 */
	public void setNegativeScore(double negativeScore) {
		this.negativeScore = negativeScore;
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
