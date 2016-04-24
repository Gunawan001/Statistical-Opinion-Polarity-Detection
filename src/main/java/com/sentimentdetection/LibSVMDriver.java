package com.sentimentdetection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class LibSVMDriver {
	public static void main(String args[]) throws IOException{
//		Runtime rt = Runtime.getRuntime();
		System.out.println("HIHIHIHI");
//		Process pr = rt.exec("java -jar map.jar time.rel test.txt debug");
//		System.out.println("HI Prathima");
		svm_parameter param=new svm_parameter();
        param.svm_type=svm_parameter.C_SVC;
        param.kernel_type=svm_parameter.RBF;
        param.gamma=0.5;
        param.nu=0.5;
        param.cache_size=20000;
        param.C=1;
        param.eps=0.001;
        param.p=0.1;
        
        
        HashMap<Integer, HashMap<Integer, Double>> featuresTraining=new HashMap<Integer, HashMap<Integer, Double>>();
        HashMap<Integer, Integer> labelTraining=new HashMap<Integer, Integer>();
        HashMap<Integer, HashMap<Integer, Double>> featuresTesting=new HashMap<Integer, HashMap<Integer, Double>>();

        HashSet<Integer> features=new HashSet<Integer>();

        //Read in training data
        BufferedReader reader=null;
        try{
            reader=new BufferedReader(new FileReader("C:/Users/pratima/Downloads/aclImdb_v1 (1)/aclImdb/train/labeledBow.feat"));
            String line=null;
            int lineNum=0;
            while((line=reader.readLine())!=null){
                featuresTraining.put(lineNum, new HashMap<Integer,Double>());
                String[] tokens=line.split("\\s+");
                int label=Integer.parseInt(tokens[0]);
                if(label<5)
                	label=0;
                else
                	label=1;
                labelTraining.put(lineNum, label);
                for(int i=1;i<tokens.length;i++){
                    String[] fields=tokens[i].split(":");
                    int featureId=Integer.parseInt(fields[0]);
                    double featureValue=Double.parseDouble(fields[1]);
                       
                    features.add(featureId);
                    featuresTraining.get(lineNum).put(featureId, featureValue);
                }
            lineNum++;
            }
//            for (Integer name: featuresTraining.keySet()){
//
//                String key =name.toString();
//                String value = featuresTraining.get(name).toString();  
//                System.out.println(key + " " + value);  
//            
//
//
//    } 
            reader.close();
        }catch (Exception e){

        }

        System.out.println(labelTraining.size());
        
        //Read in test data
        try{
            reader=new BufferedReader(new FileReader("C:/Users/pratima/Downloads/aclImdb_v1 (1)/aclImdb/test/labeledBow.feat"));
            String line=null;
            int lineNum=0;
            while((line=reader.readLine())!=null){

                featuresTesting.put(lineNum, new HashMap<Integer,Double>());
                String[] tokens=line.split("\\s+");
                for(int i=1; i<tokens.length;i++){
                    String[] fields=tokens[i].split(":");
                    int featureId=Integer.parseInt(fields[0]);
                    
                    double featureValue=Double.parseDouble(fields[1]);
                    featuresTesting.get(lineNum).put(featureId, featureValue);
                }
            lineNum++;
            }
            reader.close();
        }catch (Exception e){

        }//ENd Training Data
        
        svm_problem prob=new svm_problem();
        int numTrainingInstances=featuresTraining.keySet().size();
        prob.l=numTrainingInstances;
        prob.y=new double[prob.l];
        prob.x=new svm_node[prob.l][];
        System.out.println(prob.y);
        
        for(int i=0;i<numTrainingInstances;i++){
            HashMap<Integer,Double> tmp=featuresTraining.get(i);
            prob.x[i]=new svm_node[tmp.keySet().size()];
            int indx=0;
            for(Integer id:tmp.keySet()){
                svm_node node=new svm_node();
                node.index=id;
                node.value=tmp.get(id);
                prob.x[i][indx]=node;
                indx++;
            }

            prob.y[i]=labelTraining.get(i);
        }
        
        
        System.out.println("Processing SVM model");
        
        svm_model model=svm.svm_train(prob,param);

        for(Integer testInstance:featuresTesting.keySet()){
        	
        	//System.out.println("loop");
        	
            HashMap<Integer, Double> tmp=featuresTesting.get(testInstance);
            int numFeatures=tmp.keySet().size();
            System.out.println(numFeatures);
            svm_node[] x=new svm_node[numFeatures];
            int featureIndx=0;
            for(Integer feature:tmp.keySet()){
                x[featureIndx]=new svm_node();
                x[featureIndx].index=feature;
                x[featureIndx].value=tmp.get(feature);
                featureIndx++;
            }

            double d=svm.svm_predict(model, x);

            System.out.println(testInstance+"\t"+d);
        }


	}
}
