package bdk.typematcher2;

public interface StatefulTypeMatcher<I, S, O> {

	O match(I input, S state);
}
