package com.sentimentdetection.common;

import java.util.Arrays;

public class Document {

	private String[] inputDoc;
	private int numStopWordsCount = 0;
	private int numWords = 0;
	private double positiveWordProportion;
	private double negativeWordProportion;
	private int numNouns;
	private int numAdjectives;
	private int numVerbs;

	public Document(String[] inputDoc) {
		super();
		setInputDoc(inputDoc);
	}

	/**
	 * @return the numVerbs
	 */
	public int getNumVerbs() {
		return numVerbs;
	}

	/**
	 * @param numVerbs the numVerbs to set
	 */
	public void setNumVerbs(int numVerbs) {
		this.numVerbs = numVerbs;
	}

	/**
	 * @return the inputDoc
	 */
	public String[] getInputDoc() {
		return inputDoc;
	}

	/**
	 * @param inputDoc
	 *            the inputDoc to set
	 */
	public void setInputDoc(String[] inputDoc) {
		this.inputDoc = inputDoc;
		this.numWords = inputDoc.length;
	}

	/**
	 * @return the numStopWordsCount
	 */
	public int getNumStopWordsCount() {
		return numStopWordsCount;
	}

	/**
	 * @param numStopWordsCount
	 *            the numStopWordsCount to set
	 */
	public void setNumStopWordsCount(int numStopWordsCount) {
		this.numStopWordsCount = numStopWordsCount;
	}

	/**
	 * @return the numWords
	 */
	public int getNumWords() {
		return numWords;
	}

	/**
	 * @param numWords
	 *            the numWords to set
	 */
	public void setNumWords(int numWords) {
		this.numWords = numWords;
	}

	/**
	 * @return the positiveWordProportion
	 */
	public double getPositiveWordProportion() {
		return positiveWordProportion;
	}

	/**
	 * @param positiveWordProportion
	 *            the positiveWordProportion to set
	 */
	public void setPositiveWordProportion(double positiveWordProportion) {
		this.positiveWordProportion = positiveWordProportion;
	}

	/**
	 * @return the negativeWordProportion
	 */
	public double getNegativeWordProportion() {
		return negativeWordProportion;
	}

	/**
	 * @param negativeWordProportion
	 *            the negativeWordProportion to set
	 */
	public void setNegativeWordProportion(double negativeWordProportion) {
		this.negativeWordProportion = negativeWordProportion;
	}

	/**
	 * @return the numNouns
	 */
	public int getNumNouns() {
		return numNouns;
	}

	/**
	 * @param numNouns
	 *            the numNouns to set
	 */
	public void setNumNouns(int numNouns) {
		this.numNouns = numNouns;
	}

	/**
	 * @return the numAdjectives
	 */
	public int getNumAdjectives() {
		return numAdjectives;
	}

	/**
	 * @param numAdjectives
	 *            the numAdjectives to set
	 */
	public void setNumAdjectives(int numAdjectives) {
		this.numAdjectives = numAdjectives;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Features [ numStopWordsCount=" + numStopWordsCount
				+ ", numWords=" + numWords + ", positiveWordProportion=" + positiveWordProportion
				+ ", negativeWordProportion=" + negativeWordProportion + ", numNouns=" + numNouns + ", numAdjectives="
				+ numAdjectives +",inputDoc=" + Arrays.toString(inputDoc) +  "]";
	}

}
