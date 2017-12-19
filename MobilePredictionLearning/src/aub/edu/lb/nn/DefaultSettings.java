package aub.edu.lb.nn;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationSoftMax;

public class DefaultSettings {
	public final static double EPS = 1e-100;
	public final static int EPOCH = 100;
	
	public final static ActivationFunction activationFunction = new ActivationSigmoid();
	//public final static ActivationFunction activationFunction = new ActivationLinear();
	public final static ActivationFunction activationFunctionOuterLinear = new ActivationLinear();
	public final static ActivationFunction activationFunctionOuterSigmoid = new ActivationSigmoid();
	public final static ActivationFunction activationFunctionOuterSoftMax = new ActivationSoftMax();

}
