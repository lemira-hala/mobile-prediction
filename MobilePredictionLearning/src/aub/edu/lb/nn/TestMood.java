package aub.edu.lb.nn;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class TestMood extends Test{
public static void main(String[] args) throws FileNotFoundException {
		Data f = new Data(50);
		
		double[][] trainingInput = f.getTrainingInput();
		double[][] trainingOutput = f.getTrainingOutput();
		
		double[][] testingInput = f.getTestingInput();
		double[][] testingOutput = f.getTestingOutput();
		
		double min_mse = Integer.MAX_VALUE;
		
		TrainingNN min_nn = new TrainingNN();
		double[][] nnOutputs =  new double[f.getTestingSize()][6];
		double[][] trainingOutputMood = new double[f.getTrainingSize()][6];
		double[][] testingOutputMood = new double[f.getTestingSize()][6];
		double[][] nnBestOutput = new double[f.getTestingSize()][6];
		
		for(int i = 0; i < trainingOutputMood.length; i++) 
			//trainingOutputMood[i][0] = trainingOutput[i][1];
			trainingOutputMood[i][(int) trainingOutput[i][1]] = 1;
		
		for(int i = 0; i < testingOutput.length; i++) 
			//testingOutputMood[i][0] = trainingOutput[i][1];
			testingOutputMood[i][(int) testingOutput[i][1]] = 1;
		
		int[] epoch = {100};

		for (int units = 1; units < 100; units++) {
			for( int i = 0; i < epoch.length; i++) {
				//train
				//TrainingNN nn = new TrainingNN(21, 1, units, DefaultSettings.activationFunctionOuterLinear);
				TrainingNN nn = new TrainingNN(21, 6, units, DefaultSettings.activationFunctionOuterSoftMax);
				nn.train(trainingInput, trainingOutputMood, epoch[i], 0.000001);
						
				//test
				for( int j = 0; j < testingInput.length; j++) 
					nnOutputs[j] = nn.forwardPrograpagation(testingInput[j]);
						
				double mse = getmse(nnOutputs, testingOutputMood);
						
				if(mse < min_mse) {
					min_mse = mse;
					min_nn = nn;
					nnBestOutput = nnOutputs;
				}
			}
		}
		
	
		for(int k= 0; k < testingOutputMood.length; k++) {
			System.out.println("actual: " + Arrays.toString(testingOutputMood[k]));
			System.out.println("predicted: " + Arrays.toString(nnBestOutput[k]) + "\n");
		}	
		double accuracy = calculateAccuracy(testingOutputMood, nnBestOutput);
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
				if(Math.round(actualValue)==Math.round(nnValue))  
					accuracy++;
			}
		}
		double d = accuracy/testingOutputAge.length;
		return d*100;
	}

}