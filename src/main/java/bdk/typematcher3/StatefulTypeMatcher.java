package bdk.typematcher3;

public interface StatefulTypeMatcher<A, S, R> {
	R match(A a, S s);
}
