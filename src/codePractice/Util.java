package codePractice;

public class Util {
	/**
	 * assume a > b
	 * @param a
	 * @param b
	 * @return gcd of a and b
	 */
	public static int gcd(int a, int b) {
		assert a >= b;
		if (a == 0 || b == 0) return a + b;
		return gcd(b, a % b);
	}
}
