package bdk.typematcher3;

import java8.lang.FunctionalInterface;

public class Matches {

	@FunctionalInterface
	public interface Matchable<A> {
		A getMatch();
	}

	@FunctionalInterface
	public interface Contexted<A> {
		A getContext();
	}

	@FunctionalInterface
	public interface Stateful<A> {
		A getState();
	}

	// Combinations:

	@FunctionalInterface
	public interface ContextedMatchable<M, C> extends Matchable<M>, Contexted<C> {
	}

	@FunctionalInterface
	public interface StatefulMatchable<M, S> extends Matchable<M>, Stateful<S> {
	}

	@FunctionalInterface
	public interface StatefulContextedMatchable<M, C, S> extends Matchable<M>, Contexted<C>, Stateful<S> {
	}
	
	public static <A> Matchable<A> toMatchable(final A a) {
		return new Matchable<A>() {
			@Override
			public A getMatch() {
				return a;
			}
		};
	}
}
