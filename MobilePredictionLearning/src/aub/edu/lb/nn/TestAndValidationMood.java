package aub.edu.lb.nn;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class TestAndValidationMood extends Test{
	public static void main(String[] args) throws FileNotFoundException {
		Data f = new Data(50);

		double[][] trainingInput = f.getTrainingInput();
		double[][] trainingOutput = f.getTrainingOutput();
		
		double[][] testingInput1 = f.getTestingInput1();
		double[][] testingOutput1 = f.getTestingOutput1();
		
		double[][] validationInput=f.getValidationInput();
		double[][] validationOutput=f.getValidationOutput();
		
		double min_mse = Integer.MAX_VALUE;
		TrainingNN min_nn = new TrainingNN();
		
		double[][] nnOutputs =  new double[f.getTestingSize1()][1];
		
		double[][] trainingOutputMood = new double[f.getTrainingSize()][1];
		double[][] validationOutputMood= new double[f.getvalidationSize()][1];
		double[][] testingOutputMood = new double[f.getTestingSize1()][1];
		
		for(int i = 0; i < trainingOutputMood.length; i++) 
			trainingOutputMood[i][0] = trainingOutput[i][1];
		
		for(int i = 0; i < validationOutput.length; i++) 
			validationOutputMood[i][0] = validationOutput[i][1];
		
		for(int i = 0; i < testingOutput1.length; i++) 
			testingOutputMood[i][0] = testingOutput1[i][1];
		
		int[] epoch = {10};
		
		for (int units = 1; units < 100; units++) {
			for( int i = 0; i < epoch.length; i++) {
				//train
				TrainingNN nn = new TrainingNN(21, 1, units, DefaultSettings.activationFunctionOuterLinear);
				nn.train(trainingInput, trainingOutputMood, epoch[i], 0.000001);
				
				//validate
				for( int j = 0; j < validationInput.length; j++) 
					nnOutputs[j] = nn.forwardPrograpagation(validationInput[j]);
				
				double mse = getmse(nnOutputs, validationOutputMood);
				
				if(mse < min_mse) {
					min_mse = mse;
					min_nn = nn;
				}
			}
		}
		
		//test 
		for( int j = 0; j < testingInput1.length; j++) 
			nnOutputs[j] = min_nn.forwardPrograpagation(testingInput1[j]);
		
		for(int k= 0; k < testingOutputMood.length; k++) {
			System.out.println("actual: " + Arrays.toString(testingOutputMood[k]));
			System.out.println("predicted: " + Arrays.toString(nnOutputs[k]) + "\n");
		} 
		double accuracy = calculateAccuracy(testingOutputMood, nnOutputs);
		System.out.println("Accuracy: " + accuracy);
		System.out.println("MSE: " + min_mse);
		System.out.println("Epoch: "  + epoch[0]);
		System.out.println("Hidden neurons: " + min_nn.getHidden() + "\n");
	}
	
	public static double calculateAccuracy(double[][] testingOutputMood, double[][] nnOutput) {
		double accuracy = 0;
		for(int i = 0; i < testingOutputMood.length; i++) {
			for(int j = 0; j < testingOutputMood[0].length; j++) {
				double actualValue = testingOutputMood[i][j];
				double nnValue = nnOutput[i][j];
				if(actualValue==Math.round(nnValue))  
					accuracy++;
			}
		}
		double d = accuracy/testingOutputMood.length;
		return d*100;
	}
}