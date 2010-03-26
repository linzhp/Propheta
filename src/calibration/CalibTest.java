package calibration;

import org.apache.commons.math.stat.regression.SimpleRegression;

public class CalibTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleRegression regression = new SimpleRegression();
		regression.addData(Math.log(134.47), Math.log(1854.55/1.89)-0.01*29.28*Math.log(134.47));
		regression.addData(Math.log(132), Math.log(258.51/0.49)-0.01*16.72*Math.log(132));
		regression.addData(Math.log(44.03), Math.log(201/1.06)-0.01*22.48*Math.log(44.03));
		regression.addData(Math.log(3.57), Math.log(58.87/5.05)-0.01*18.19*Math.log(3.57));
		regression.addData(Math.log(380.8), Math.log(9661.02/3.05)-0.01*26.77*Math.log(380.8));
		regression.addData(Math.log(980), Math.log(7021.28/0.92)-0.01*25.21*Math.log(980));
		regression.addData(Math.log(11.186), Math.log(91.67/2.45)-0.01*23.5*Math.log(11.186));
		regression.addData(Math.log(61.56), Math.log(689.66/2.38)-0.01*26.48*Math.log(61.56));
		System.out.println(regression.getIntercept());
		System.out.println(regression.getSlope());
//		double sum=0;
//		sum += beta(1854.55,134.47,1.89,29.28);
//		System.out.println(sum);
//		sum += beta(258.51, 132, 0.49,16.72);
//		sum += beta(201,44.03,1.06,22.48);
//		sum += beta(58.87,3.57,5.05,18.19);
//		sum += beta(9661.02,380.8,3.05,26.77);
//		sum += beta(7021.28,980,0.92,25.21);
//		sum += beta(91.67,11.186,2.45,23.5);
//		sum += beta(689.66,61.56,2.38,26.48);
	}

	
	public static double beta(double pm, double ksloc, double em, double sf){
		return Math.log(pm)-((0.91+sf)*Math.log(ksloc)+Math.log(em));
	}
}
