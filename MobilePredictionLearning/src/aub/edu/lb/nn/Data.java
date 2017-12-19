package aub.edu.lb.nn;


import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Data {
	int outputSize = 3;
	int inputSize = 21;
	double[][] trainingInput;
	double [][] trainingOutput;
	double[][] testingInput;
	double [][] testingOutput;
	double[][] testingInput1;
	double [][] testingOutput1;
	double[][] validationInput;
	double [][] validationOutput;
	int training;
	int testing;
	int testing1;
	int validation;
	int size;
	
	public int getTestingSize() {
		return testing;
	}
	
	public int getTrainingSize() {
		return training;
	}
	
	public int getTestingSize1() {
		//testing1= (int) Math.ceil(testing/2);
		return testing1;
	}
	
	public int getvalidationSize() {
		//validation= testing-testing1;
		return validation;
	}
	
	public Data(int trainPercentage) throws FileNotFoundException {
		Scanner s = new Scanner (new File("data/size.txt")); 
		String str = s.nextLine();
		size = Integer.parseInt(str);
		s.close(); 
		        
		double p = ((double) trainPercentage)/100;
		        
		training = (int) Math.ceil(size*p);
		testing = size - training;
		        
		trainingOutput = new double[training][outputSize];
		trainingInput = new double[training][inputSize];
		
		testingOutput = new double[testing][outputSize];
		testingInput = new double[testing][inputSize];
		
		testingOutput1 = new double[testing/2][outputSize];
		testingInput1 = new double[testing/2][inputSize];
		
		validationInput = new double[testing/2][inputSize];
		validationOutput = new double[testing/2][outputSize];
		
		testing1= testing/2;
		validation= testing-testing1;

	
	}
	public void  getInputs() throws FileNotFoundException {
	        Scanner file = new Scanner (new File("data/input.txt")); 
	        for(int i = 0; i < size ;i++) {
	            String str = file.nextLine();
	            String[] tokens= str.split(" ");
	            for (int j = 0; j < inputSize ;j++) {
	            if(i < training) this.trainingInput[i][j] = Double.parseDouble(tokens[j]);
	            else if (i < size && i >= training) this.testingInput[i-training][j] = Double.parseDouble(tokens[j]);
	            }         
	        }
	        file.close(); 
	}
	
	public double[][] getTrainingInput() throws FileNotFoundException {
		getInputs();
		return this.trainingInput;
	}
	
	public double[][] getTestingInput() throws FileNotFoundException {
		getInputs();
		return this.testingInput;
	}
	
	public void  getOutputs() throws FileNotFoundException {
	        Scanner file = new Scanner (new File("data/output.txt")); 
	        for(int i = 0; i < size ;i++) {
	            String str = file.nextLine();
	            String[] tokens= str.split(" ");
	            for (int j = 0; j < outputSize ;j++) {
	            if(i < training) this.trainingOutput[i][j] = Double.parseDouble(tokens[j]);
	            else if (i < size && i >= training) this.testingOutput[i-training][j] = Double.parseDouble(tokens[j]);
	            }         
	        }
	        file.close(); 
	}
	public double[][] getTrainingOutput() throws FileNotFoundException {
		getOutputs();
		return this.trainingOutput;
	}
	public double[][] getTestingOutput() throws FileNotFoundException {
		getOutputs();
		return this.testingOutput;
	}
	public int getSize() {
		return size;
	}
	public void splitTesting() {
		for( int i = 0; i < testing; i++) {
			if (i < testing/2) {
				validationInput[i] = testingInput[i];
				validationOutput[i] = testingOutput[i];
			} else if (i < testing && i >= testing/2) {
				testingInput1[i-testing/2] = testingInput[i];
				testingOutput1[i-testing/2] = testingOutput[i];
			}
		}
	}
	
	public double[][] getTestingOutput1() {
		splitTesting();
		return this.testingOutput1;
	}
	
	public double[][] getTestingInput1() {
		splitTesting();
		return this.testingInput1;
	}
	
	public double[][] getValidationOutput() {
		splitTesting();
		return this.validationOutput;
	}
	
	public double[][] getValidationInput() {
		splitTesting();
		return this.validationInput;
	}

}