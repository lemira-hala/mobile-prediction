package aub.edu.lb.nn;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class TestAge extends Test {

	public static void main(String[] args) throws FileNotFoundException {
		
		Data f = new Data(50);
		
		double[][] trainingInput = f.getTrainingInput();
		double[][] trainingOutput = f.getTrainingOutput();
		
		double[][] testingInput = f.getTestingInput();
		double[][] testingOutput = f.getTestingOutput();
		
		double min_mse = Integer.MAX_VALUE;
		
		TrainingNN min_nn = new TrainingNN();
		double[][] nnOutputs =  new double[f.getTestingSize()][1];
		double[][] trainingOutputAge = new double[f.getTrainingSize()][1];
		double[][] testingOutputAge = new double[f.getTestingSize()][1];
		double[][] nnBestOutput = new double[f.getTestingSize()][1];
		
		for(int i = 0; i < trainingOutputAge.length; i++) 
			trainingOutputAge[i][0] = trainingOutput[i][0];
		
		for(int i = 0; i < testingOutput.length; i++) 
			testingOutputAge[i][0] = testingOutput[i][0];
		
		int[] epoch = {70};

		for (int units = 1; units < 50; units++) {
			for( int i = 0; i < epoch.length; i++) {
				//train
				TrainingNN nn = new TrainingNN(21, 1, units, DefaultSettings.activationFunctionOuterLinear);
				nn.train(trainingInput, trainingOutputAge, epoch[i], 0.000001);
						
				//test
				for( int j = 0; j < testingInput.length; j++) 
					nnOutputs[j] = nn.forwardPrograpagation(testingInput[j]);
						
				double mse = getmse(nnOutputs, testingOutputAge);
						
				if(mse < min_mse) {
					min_mse = mse;
					min_nn = nn;
					nnBestOutput = nnOutputs;
				}
			}
		}
		
	
		for(int k= 0; k < testingOutputAge.length; k++) {
			System.out.println("actual: " + Arrays.toString(testingOutputAge[k]));
			System.out.println("predicted: " + Arrays.toString(nnBestOutput[k]) + "\n");
		}	
		double accuracy = calculateAccuracy(testingOutputAge, nnBestOutput);
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
				if(lessThan10(actualValue)&&lessThan10(nnValue)) 
						accuracy++;
				if(greaterThan30(actualValue)&&greaterThan30(nnValue)) 
						accuracy++;
				if(_10to16(actualValue)&&_10to16(nnValue)) 
						accuracy++;
				if(_17to25(actualValue)&&_17to25(nnValue))
						accuracy++;
				if(_26to29(actualValue)&&_26to29(nnValue)) 
						accuracy++;
			}
		}
		double d = accuracy/testingOutputAge.length;
		return d*100;
	}
	
	public static boolean lessThan10(double value) {
		if(Math.round(value)>=10) return false;
		return true;
	}
	
	public static boolean greaterThan30(double value) {
		if(Math.round(value)<=30) return false;
		return true;
	}
	
	public static boolean _10to16(double value) {
		if((Math.round(value) >= 10) && (Math.round(value) <= 17)) return true;
		return false;
	}
	
	public static boolean _17to25(double value) {
		if((Math.round(value) >= 16) && (Math.round(value) <= 23)) return true;
		return false;
	}
	
	public static boolean _26to29(double value) {
		if((Math.round(value) >= 24) && (Math.round(value) <= 29)) return true;
		return false;
	}
}

	