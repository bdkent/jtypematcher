package bdk.typematcher;

public interface ContextedTypeMatcher<A, B, C> {

	C handle(A a, B b);
}
