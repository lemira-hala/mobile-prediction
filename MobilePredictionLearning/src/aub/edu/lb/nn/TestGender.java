package aub.edu.lb.nn;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class TestGender extends Test {
	public static void main(String[] args) throws FileNotFoundException {
		
		Data f = new Data(70);
		
		double[][] trainingInput = f.getTrainingInput();
		double[][] trainingOutput = f.getTrainingOutput();
		
		double[][] testingInput = f.getTestingInput();
		double[][] testingOutput = f.getTestingOutput();
		
		double min_mse = Integer.MAX_VALUE;
		
		TrainingNN min_nn = new TrainingNN();
		double[][] nnOutputs =  new double[f.getTestingSize()][1];
		double[][] trainingOutputGender = new double[f.getTrainingSize()][1];
		double[][] testingOutputGender = new double[f.getTestingSize()][1];
		double[][] nnBestOutput = new double[f.getTestingSize()][1];
		
		for(int i = 0; i < trainingOutputGender.length; i++) 
			trainingOutputGender[i][0] = trainingOutput[i][2];
		
		for(int i = 0; i < testingOutput.length; i++) 
			testingOutputGender[i][0] = testingOutput[i][2];
		
		int[] epoch = {10000};

		for (int units = 24; units <= 24; units+=20) {
			for( int i = 0; i < epoch.length; i++) {
				// train
				TrainingNN nn = new TrainingNN(21, 1, units, DefaultSettings.activationFunctionOuterLinear);
				nn.train(trainingInput, trainingOutputGender, epoch[i], 0.000001);
						
				// test
				for( int j = 0; j < testingInput.length; j++) 
					nnOutputs[j] = nn.forwardPrograpagation(testingInput[j]);
						
				double mse = getmse(nnOutputs, testingOutputGender);
						
				if(mse < min_mse) {
					min_mse = mse;
					min_nn = nn;
					nnBestOutput = nnOutputs;
				}
			}
		}
		
		
		for(int k= 0; k < testingOutputGender.length; k++) {
			System.out.println("actual: " + Arrays.toString(testingOutputGender[k]));
			System.out.println("predicted: " + Arrays.toString(nnBestOutput[k]) + "\n");
		}	
		double accuracy = calculateAccuracy(testingOutputGender, nnBestOutput);
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
				if((isFemale(actualValue)&&isFemale(nnValue)) || (isMale(actualValue)&&isMale(nnValue)))  
						accuracy++;
			}
		}
		double d = accuracy/testingOutputAge.length;
		return d * 100;
	}
	
	public static boolean isFemale(double value) {
		if(value < 0.5) return false;
		return true;
	}
	
	public static boolean isMale(double value) {
		if(value>=0.5) return false;
		return true;
	}
}

	