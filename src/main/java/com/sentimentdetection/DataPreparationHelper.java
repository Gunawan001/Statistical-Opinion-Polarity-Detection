package com.sentimentdetection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.sentimentdetection.common.Document;
import com.sentimentdetection.common.SentimentType;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * MCAP Logistic Regression algorithm with L2 regularization
 * 
 * This class is implementation of Text Classifier that uses Logistic Regression
 * approach. This approach involves calculation of P(y|x) based on trained
 * model. We use gradient ascent approach in order to reach the global maxima.
 * 
 * @author Kanchan Waikar Date Created : Mar 15, 2016 - 6:44:13 PM
 *
 */
public class DataPreparationHelper {
	public static final String NOUN_PREFIX = "NN";
	public static final String ADJECTIVE_PREFIX = "JJ";
	public static final String VERB_PREFIX = "VB";
	public static final String NON_ALPHABET_CHARS = "[^\\dA-Za-z ]";
	public static final String SIMPLE_TOKENIZER_REGEX = "(\r|\n|\f)";
	public static final String LINE_TOKENIZER_REGEX = "(\r|\n|\f)";
	private Set<String> stopWords = new HashSet<String>();

	WordSentimentExtractor sentiExtractor = new WordSentimentExtractor();

	MaxentTagger tagger = null;

	/**
	 * This method initializes classifier with an optional stopwords file
	 * 
	 * @param stopWordsFile
	 * @throws IOException
	 */
	private void initialize(String stopWordsFile) throws IOException {
		InputStream ioStream = getClass().getClassLoader().getResourceAsStream(stopWordsFile);
		String stopWordString = IOUtils.toString(ioStream);
		stopWords = new HashSet<String>();
		for (String word : stopWordString.split("[\r|\n]")) {
			if (word.trim().length() > 1) {
				stopWords.add(word.trim().toLowerCase());
			}
		}

		tagger = new MaxentTagger(
				this.getClass().getResource("/POS_Tagged_Model/english-bidirectional-distsim.tagger").getFile());
	}

	/**
	 * This method accepts Document and sets numStopWords in the same.
	 * 
	 * @param featureDoc
	 * @return
	 */
	public void setNumStopWords(Document featureDoc) {
		int numStopWordsFound = 0;
		int numCount=0;
		for (String sentence : featureDoc.getInputDoc()) {
			String[] split = sentence.split("( |\t)");
			for (String word : split) {
				numCount++;
				if (stopWords.contains(word.trim().toLowerCase())) {
					numStopWordsFound++;
				}
			}
		}
		featureDoc.setStopWordsProportion((double)numStopWordsFound/numCount);
		featureDoc.setNumWords(numCount);
	}

	/**
	 * This method sets NLP features of the document in the POJO.
	 * 
	 * @param featuresDoc
	 */
	public void setNLPFeatures(Document featuresDoc) {

		int numNounEntities = 0;
		int numVerbs = 0;
		int numAdjectives = 0;
		int numPositives = 0;
		int numNegatives = 0;
		int numNeutrals = 0;
		for (String sentence : featuresDoc.getInputDoc()) {
			List<HasWord> sent = Sentence.toWordList(sentence.split(" "));
			List<TaggedWord> taggedSent = tagger.tagSentence(sent);
			for (TaggedWord tw : taggedSent) {
				String word = tw.word().replaceAll(NON_ALPHABET_CHARS, "");
				if (StringUtils.isNotBlank(word)) {
					if (!stopWords.contains(word.toLowerCase()) && word.trim().length() > 1) {
						if (tw.tag().startsWith(NOUN_PREFIX)) {
							numNounEntities++;
						} else if (tw.tag().startsWith(ADJECTIVE_PREFIX)) {
							numAdjectives++;
							SentimentType sentimentType = sentiExtractor.getSentimentForAdjective(tw.word(), sentence);
							switch (sentimentType) {
							case POSITIVE:
								numPositives++;
								break;
							case NEGATIVE:
								numNegatives++;
								break;
							case NEUTRAL:
								numNeutrals++;
								break;
							}
						} else if (tw.tag().startsWith(VERB_PREFIX)) {
							numVerbs++;
							SentimentType sentimentType = sentiExtractor.getSentimentForAdjective(tw.word(), sentence);
							switch (sentimentType) {
							case POSITIVE:
								numPositives++;
								break;
							case NEGATIVE:
								numNegatives++;
								break;
							case NEUTRAL:
								numNeutrals++;
								break;
							}
						}
					}
				}
			}
		}
		featuresDoc.setPositiveWordProportion(((double)numPositives/(numNeutrals+numPositives+numNegatives)));
		featuresDoc.setNegativeWordProportion(((double)numNegatives/(numNeutrals+numPositives+numNegatives)));
		featuresDoc.setNumAdjectives(numAdjectives);
		featuresDoc.setNumNouns(numNounEntities);
		featuresDoc.setNumVerbs(numVerbs);
	}

	public static void main(String[] args) throws IOException {

		DataPreparationHelper helper = new DataPreparationHelper();
		helper.loadStopWordsAndSentiments();

		if (args.length != 1) {
			System.out.println("Please provide input directory path for training:");
			System.exit(0);
		}
		File parentFolder = new File(args[0]);
		if (parentFolder.exists()) {
			System.out.println("Training on");
			File[] classes = parentFolder.listFiles();
			for (File file : classes) {
				Document document = new Document(FileUtils.readFileToString(file).split(SIMPLE_TOKENIZER_REGEX));
				helper.setNumStopWords(document);
				helper.setNLPFeatures(document);
				System.out.println(document);
			}
		}
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public void loadStopWordsAndSentiments() throws IOException {
		sentiExtractor.loadSentiments("/SentiWordNet_3.0.0_20130122.txt");

		String stopWordsFilePrefix = "stopwords.txt";
		initialize(stopWordsFilePrefix);
	}
}
