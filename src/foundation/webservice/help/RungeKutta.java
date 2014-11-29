package foundation.webservice.help;

import java.util.ArrayList;
import java.util.List;

public class RungeKutta {

	// /**
	// * @param args
	// */
	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	// List<Double> encodes=new ArrayList<Double>();
	// //1,,,,,
	// encodes.add(1.497657133494571);
	// encodes.add(1.6938405326462087);
	// encodes.add(1.8316290033234823);
	// encodes.add(0.5404420576905522);
	// encodes.add(0.1889266916520338);
	// double[] result =calcuFitness(encodes);
	// for(int i=0;i<result.length;i++){
	// System.out.println(result[i]);
	// }
	// }
	public static double[] calcuFitness(List<Double> encodes) {
		double fitness = 0.1;
		// 根据规则序列执行结果计算适应
		// double measure_x1[] = dataOpt.getMeasure_x1();
		int N = 12;
		// int N = measure_x1.length;
		double estimated_x1[] = new double[N];// 心率
		double estimated_x2[] = new double[N];// 速度
		estimated_x1[0] = 0;
		estimated_x2[0] = 0;
		double h = 1;
		double f1, f2, f3, f4, g1, g2, g3, g4;
		Kutta k = new Kutta();
		int len = 12;
		for (int j = 1; j < len; j++) {
			f1 = k.x1(estimated_x1[j - 1], estimated_x2[j - 1], encodes.get(0),
					encodes.get(1), j);
			g1 = k.x2(estimated_x1[j - 1], estimated_x2[j - 1], encodes.get(2),
					encodes.get(3), encodes.get(4));
			f2 = k.x1(estimated_x1[j - 1] + h / 2 * f1, estimated_x2[j - 1] + h
					/ 2 * g1, encodes.get(0), encodes.get(1), j);
			g2 = k.x2(estimated_x1[j - 1] + h / 2 * f1, estimated_x2[j - 1] + h
					/ 2 * g1, encodes.get(2), encodes.get(3), encodes.get(4));
			f3 = k.x1(estimated_x1[j - 1] + h / 2 * f2, estimated_x2[j - 1] + h
					/ 2 * g2, encodes.get(0), encodes.get(1), j);
			g3 = k.x2(estimated_x1[j - 1] + h / 2 * f2, estimated_x2[j - 1] + h
					/ 2 * g2, encodes.get(2), encodes.get(3), encodes.get(4));
			f4 = k.x1(estimated_x1[j - 1] + h * f3, estimated_x2[j - 1] + h
					* g3, encodes.get(0), encodes.get(1), j);
			g4 = k.x2(estimated_x1[j - 1] + h * f3, estimated_x2[j - 1] + h
					* g3, encodes.get(2), encodes.get(3), encodes.get(4));
			estimated_x1[j] = estimated_x1[j - 1] + h / 6
					* (f1 + 2 * f2 + 2 * f3 + f4);
			estimated_x2[j] = estimated_x2[j - 1] + h / 6
					* (g1 + 2 * g2 + 2 * g3 + g4);
		}
		return estimated_x1;
	}

	// 时间下的心率
	public static double[] calcuFitness(List<Double> encodes, double[] speed) {
		double fitness = 0.1;
		// 根据规则序列执行结果计算适应度()
		// double measure_x1[] = dataOpt.getMeasure_x1();
		int N = 11;
		// int N = measure_x1.length;
		double estimated_x1[] = new double[N];// 心率
		double estimated_x2[] = new double[N];
		double[] u = new double[N];
		u = speed;
		estimated_x1[0] = 0;
		estimated_x2[0] = 0;
		double h = 1;
		double f1, f2, f3, f4, g1, g2, g3, g4;
		Kutta k = new Kutta();
		int len = 11;
		for (int j = 1; j < len; j++) {
			f1 = k.x1(estimated_x1[j - 1], estimated_x2[j - 1], encodes.get(0),
					encodes.get(1), u[j - 1]);
			g1 = k.x2(estimated_x1[j - 1], estimated_x2[j - 1], encodes.get(2),
					encodes.get(3), encodes.get(4));
			f2 = k.x1(estimated_x1[j - 1] + h / 2 * f1, estimated_x2[j - 1] + h
					/ 2 * g1, encodes.get(0), encodes.get(1), u[j - 1]);
			g2 = k.x2(estimated_x1[j - 1] + h / 2 * f1, estimated_x2[j - 1] + h
					/ 2 * g1, encodes.get(2), encodes.get(3), encodes.get(4));
			f3 = k.x1(estimated_x1[j - 1] + h / 2 * f2, estimated_x2[j - 1] + h
					/ 2 * g2, encodes.get(0), encodes.get(1), u[j - 1]);
			g3 = k.x2(estimated_x1[j - 1] + h / 2 * f2, estimated_x2[j - 1] + h
					/ 2 * g2, encodes.get(2), encodes.get(3), encodes.get(4));
			f4 = k.x1(estimated_x1[j - 1] + h * f3, estimated_x2[j - 1] + h
					* g3, encodes.get(0), encodes.get(1), u[j - 1]);
			g4 = k.x2(estimated_x1[j - 1] + h * f3, estimated_x2[j - 1] + h
					* g3, encodes.get(2), encodes.get(3), encodes.get(4));
			estimated_x1[j] = estimated_x1[j - 1] + h / 6
					* (f1 + 2 * f2 + 2 * f3 + f4);
			estimated_x2[j] = estimated_x2[j - 1] + h / 6
					* (g1 + 2 * g2 + 2 * g3 + g4);
		}
		return estimated_x1;

	}
}
