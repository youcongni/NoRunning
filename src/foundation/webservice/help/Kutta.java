package foundation.webservice.help;

public class Kutta {
	public double x1(double x1, double x2, double a1, double a2, double u) {
		return (-1) * a1 * x1 + a2 * (x2 + u*u);
	}
	
	public double x2(double x1, double x2, double a3, double a4, double a5) {
		return (-1) * x2 + (a4 * x1) / (1 + Math.exp(a5 - x1));
	}
}
