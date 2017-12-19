package aub.edu.lb.nn;

public class Test {
	
	public static double getmse(double[][] predicted, double[][] actual) {
		double error = 0;
		if(predicted.length != actual.length) {
			System.out.println("Not the same length");
			return -1;
		}
		
		for(int i = 0; i < predicted.length; i++){
			for (int j = 0; j < 1; j ++) 
				error += Math.pow((actual[i][j] - predicted[i][j]),2);
		}
		//NOTE: we can add do to return the rmse (Root Mean Squared Error)
		return Math.sqrt(error/predicted.length);
		//return error/predicted.length;
	
	}

}
