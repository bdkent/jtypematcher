package bdk.typematcher2;

public interface TypeMatcher<I, O> {

	O match(I input);
}
