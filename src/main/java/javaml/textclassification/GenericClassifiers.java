package javaml.textclassification;


import java.io.File;

import libsvm.LibSVM;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.ARFFHandler;

 
public class GenericClassifiers {
    /**
     * Shows the default usage of the KNN algorithm.
     */
    public static void main(String[] args)throws Exception {
    	

    	Dataset trainingData = ARFFHandler.loadARFF(new File(args[0]), 7);
        /*
         * Contruct a KNN classifier that uses 5 neighbors to make a decision.
         */
        Classifier knn = new LibSVM();
        knn.buildClassifier(trainingData);

        /*
         * Load a data set for evaluation, this can be a different one, but we
         * will use the same one.
         */
       
        Dataset testData = ARFFHandler.loadARFF(new File( args[1]), 7);
        /* Counters for correct and wrong predictions. */
       double accurcy= predictAndReturnAccuracy(knn, trainingData);
       System.out.println("Training Data" +accurcy);
         accurcy= predictAndReturnAccuracy(knn, testData);
       System.out.println("Training Data" +accurcy);
    }

	/**
	 * @param knn
	 * @param dataForClassification
	 */
	public static double predictAndReturnAccuracy(Classifier knn, Dataset dataForClassification) {
		int correct = 0, wrong = 0;
        /* Classify all instances and check with the correct class values */
        for (Instance inst : dataForClassification) {
            Object predictedClassValue = knn.classify(inst);
            Object realClassValue = inst.classValue();
        //    System.out.println(predictedClassValue+ " : "+ realClassValue);
            if (predictedClassValue.equals(realClassValue))
                correct++;
            else
                wrong++;
        }
        System.out.println("Correct predictions  " + correct);
        System.out.println("Wrong predictions " + wrong);
        double accuracy =(double)100*correct/(correct+wrong);
        System.out.println("Accuracy: "+(double)100*correct/(correct+wrong));
        return accuracy;
	}

}