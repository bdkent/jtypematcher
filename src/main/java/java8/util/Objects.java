package java8.util;

public class Objects {

	public static <T> T requireNonNull(T obj) {
		if(obj == null) {
			throw new NullPointerException("null");
		}
		else {
			return obj;
		}
	}
}
