package aub.edu.lb.nn;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class TestAndValidationGender extends Test{
	public static void main(String[] args) throws FileNotFoundException {
	Data f = new Data(80);
	
	double[][] trainingInput = f.getTrainingInput();
	double[][] trainingOutput = f.getTrainingOutput();
	
	double[][] testingInput1 = f.getTestingInput1();
	double[][] testingOutput1 = f.getTestingOutput1();
	
	double[][] validationInput=f.getValidationInput();
	double[][] validationOutput=f.getValidationOutput();
	
	double min_mse = Integer.MAX_VALUE;
	TrainingNN min_nn = new TrainingNN();
	
	double[][] nnOutputs =  new double[f.getTestingSize1()][1];
	
	double[][] trainingOutputGender = new double[f.getTrainingSize()][1];
	double[][] validationOutputGender= new double[f.getvalidationSize()][1];
	double[][] testingOutputGender = new double[f.getTestingSize1()][1];
	
	for(int i = 0; i < trainingOutputGender.length; i++) 
		trainingOutputGender[i][0] = trainingOutput[i][2];
	
	for(int i = 0; i < validationOutput.length; i++) 
		validationOutputGender[i][0] = validationOutput[i][2];
	
	for(int i = 0; i < testingOutput1.length; i++) 
		testingOutputGender[i][0] = testingOutput1[i][2];
	
	int[] epoch = {500};
	
	
	for (int units = 1; units < 100; units++) {
		for( int i = 0; i < epoch.length; i++) {
			//train
			TrainingNN nn = new TrainingNN(21, 1, units, DefaultSettings.activationFunctionOuterSigmoid);
			nn.train(trainingInput, trainingOutputGender, epoch[i], 0.000001);
			
			//validate
			for( int j = 0; j < validationInput.length; j++) 
				nnOutputs[j] = nn.forwardPrograpagation(validationInput[j]);
			
			//test 
			//for( int j = 0; j < testingInput1.length; j++) 
			//	nnOutputs[j] = nn.forwardPrograpagation(testingInput1[j]);
			
			double mse = getmse(nnOutputs, validationOutputGender);
			
			if(mse < min_mse) {
				min_mse = mse;
				// get the last nn
				min_nn = nn;
			}
		}
	}
	
	
	for( int j = 0; j < testingInput1.length; j++) 
		nnOutputs[j] = min_nn.forwardPrograpagation(testingInput1[j]);
	
	for(int k= 0; k < testingOutputGender.length; k++) {
		System.out.println("actual: " + Arrays.toString(testingOutputGender[k]));
		System.out.println("predicted: " + Arrays.toString(nnOutputs[k]) + "\n");
	} 
	
	double accuracy = calculateAccuracy(testingOutputGender, nnOutputs);
	System.out.println("Accuracy: " + accuracy);
	System.out.println("MSE: " + min_mse);
	System.out.println("Epoch: "  + epoch[0]);
	System.out.println("Hidden neurons: " + min_nn.getHidden() + "\n");
	}
	
	public static double calculateAccuracy(double[][] testingOutputAge, double[][] nnOutput) {
		double accuracy = 0;
		for(int i = 0; i < testingOutputAge.length; i++) {
			for(int j = 0; j < testingOutputAge[0].length; j++) {
				double actualValue = testingOutputAge[i][j];
				double nnValue = nnOutput[i][j];
				if(isFemale(actualValue)&&isFemale(nnValue))  
					accuracy++;
				if(isMale(actualValue)&&isMale(nnValue)) 
					accuracy++;
				}
		}
		double d = accuracy/testingOutputAge.length;
		return d*100;
	}
	
	public static boolean isFemale(double value) {
		if(value<0.5) return false;
		return true;
	}
	public static boolean isMale(double value) {
		if(value>=0.5) return false;
		return true;
	}
}