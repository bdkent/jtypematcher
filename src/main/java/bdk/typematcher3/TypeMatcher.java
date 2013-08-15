package bdk.typematcher3;

import java8.lang.FunctionalInterface;

@FunctionalInterface
public interface TypeMatcher<A, B> {
	B match(A a);
}
