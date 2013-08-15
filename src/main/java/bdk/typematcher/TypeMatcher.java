package bdk.typematcher;

public interface TypeMatcher<A,B> {

	B handle(A a);
}
