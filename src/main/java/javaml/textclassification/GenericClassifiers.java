package javaml.textclassification;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Random;

import libsvm.LibSVM;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KDtreeKNN;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.ARFFHandler;
import net.sf.javaml.tools.weka.WekaClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;

public class GenericClassifiers {
	/**
	 * Shows the default usage of the KNN algorithm.
	 */
	public static void main(String[] args) throws Exception {

		/*
		 * Contruct a KNN classifier that uses 5 neighbors to make a decision.
		 */
		String trainingDataFilePath = args[0];
		String testDataFilePath = args[1];

		Logistic wekaClassifier = new Logistic();
		Classifier wekaClassifierWrapper = new WekaClassifier(wekaClassifier);
		TrainModelAndPrintAccuracyForTrainingAndTestingDataSet(trainingDataFilePath, testDataFilePath,
				wekaClassifierWrapper);

		NaiveBayes naiveClassifier = new NaiveBayes();
		wekaClassifierWrapper = new WekaClassifier(naiveClassifier);
		TrainModelAndPrintAccuracyForTrainingAndTestingDataSet(trainingDataFilePath, testDataFilePath,
				wekaClassifierWrapper);

		Classifier classifier = new KDtreeKNN(75);
		TrainModelAndPrintAccuracyForTrainingAndTestingDataSet(trainingDataFilePath, testDataFilePath, classifier);

		classifier = new KNearestNeighbors(75);
		TrainModelAndPrintAccuracyForTrainingAndTestingDataSet(trainingDataFilePath, testDataFilePath, classifier);

		classifier = new LibSVM();
		TrainModelAndPrintAccuracyForTrainingAndTestingDataSet(trainingDataFilePath, testDataFilePath, classifier);

	}

	/**
	 * This method trains model and prints accuracy for both data sets.
	 * 
	 * @param trainingDataFilePath
	 * @param testDataFilePath
	 * @param classifier
	 * @throws FileNotFoundException
	 */
	public static void TrainModelAndPrintAccuracyForTrainingAndTestingDataSet(String trainingDataFilePath,
			String testDataFilePath, Classifier classifier) throws FileNotFoundException {
		Dataset trainingData = ARFFHandler.loadARFF(new File(trainingDataFilePath), 7);
		classifier.buildClassifier(trainingData);
		Dataset testData = ARFFHandler.loadARFF(new File(testDataFilePath), 7);

		double accurcy = predictAndReturnAccuracy(classifier, trainingData);
		System.out.println("Training Data Accuracy:" + accurcy);
		accurcy = predictAndReturnAccuracy(classifier, testData);
		System.out.println("Test Data Accuracy:" + accurcy);
	}

	/**
	 * @param knn
	 * @param dataForClassification
	 */
	public static double predictAndReturnAccuracy(Classifier knn, Dataset dataForClassification) {
		int wrongClassifications = 0;
		for (Instance inst : dataForClassification) {

			if (!inst.classValue().equals(knn.classify(inst))) {
				wrongClassifications++;
			}
		}
		System.out.println("Correct predictions  " + (dataForClassification.size() - wrongClassifications));
		System.out.println("Wrong predictions " + wrongClassifications);

		double accuracy = (double) 100 * (dataForClassification.size() - wrongClassifications)
				/ dataForClassification.size();
		System.out.println("Accuracy: "
				+ (double) 100 * (dataForClassification.size() - wrongClassifications) / dataForClassification.size());

		CrossValidation cv = new CrossValidation(knn);
		Map<Object, PerformanceMeasure> p = cv.crossValidation(dataForClassification, 5, new Random(1));
		System.out.println("Performance Measure");
		System.out.println(p);

		return accuracy;
	}

}