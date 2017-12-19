package aub.edu.lb.nn;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;


public class TrainingNN {
	
	private static BasicNetwork neuralNetwork; 
	private int inputLength, outputLength;
	private double eps = DefaultSettings.EPS;
	private int epoch = DefaultSettings.EPOCH;
	private int numberOfNeuronsHidden;

	/**
	 * 
	 * @param inputLength
	 * @param outputLength
	 * @param numberOfNeuronsHidden
	 */
	public TrainingNN(int inputLength, int outputLength, int numberOfNeuronsHidden, ActivationFunction outerFunction) {
		this.inputLength = inputLength;
		this.outputLength = outputLength;
		this.numberOfNeuronsHidden = numberOfNeuronsHidden;
		initializeNeuralNetworks(outerFunction);
	}
	
	public TrainingNN() {
		// TODO Auto-generated constructor stub
	}

	public int getEpoch( ) {
		return this.epoch;
	}
	
	public void setEpoch(int epoch) {
		this.epoch = epoch;
	}
	
	public int getHidden( ) {
		return this.numberOfNeuronsHidden;
	}
	/**
	 * 
	 * @param inputTraining
	 * @param outputTraining
	 * @param epoch
	 * @param eps
	 */
	public void train(double[][] inputTraining, double[][] outputTraining, int epoch, double eps) {
		EncogHelper.learning(neuralNetwork, inputTraining, outputTraining, epoch, eps);
	}
	
	public void train(double[][] inputTraining, double[][] outputTraining) {
		train(inputTraining, outputTraining, epoch, eps);
	}
	
	public double[] forwardPrograpagation(double[] input) {
		return EncogHelper.forwardPropagation(neuralNetwork, input);
	}
		
	private void initializeNeuralNetworks(ActivationFunction outerFunction) {
		neuralNetwork = new BasicNetwork();
		neuralNetwork.addLayer(new BasicLayer(null, true, inputLength));
		neuralNetwork.addLayer(new BasicLayer(DefaultSettings.activationFunction, true, numberOfNeuronsHidden));
		neuralNetwork.addLayer(new BasicLayer(outerFunction, false, outputLength));
		neuralNetwork.getStructure().finalizeStructure();
		neuralNetwork.reset();
	}
	
	public String toString() {
		return "eps: " + eps + "/nepoch: " + epoch + "/n numberOfNeuronsHidden: " + numberOfNeuronsHidden;
	}

}
