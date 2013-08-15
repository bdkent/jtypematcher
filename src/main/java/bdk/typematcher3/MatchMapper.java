package bdk.typematcher3;

import java8.lang.FunctionalInterface;

@FunctionalInterface
public interface MatchMapper<A,B> {
	B map(A a);
}
