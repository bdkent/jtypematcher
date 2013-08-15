package bdk.typematcher3;

import java8.util.Optional;
import java8.util.function.Supplier;

import bdk.typematcher3.Factories.StatefulTypeMatcherFactory;
import bdk.typematcher3.Factories.TypeMatcherFactory;
import bdk.typematcher3.Matches.ContextedMatchable;
import bdk.typematcher3.Matches.Matchable;
import bdk.typematcher3.Matches.StatefulContextedMatchable;
import bdk.typematcher3.Matches.StatefulMatchable;

public class Prototypes {

	// direct
	
	public interface OpenPrototype<M, R> extends TypeMatcherFactory<M, Optional<R>> {
		<MX extends M> OpenPrototype<M, R> caseOf(Class<MX> matchType, Handler<Matchable<MX>, R> handler);
		TypeMatcherFactory<M, R> otherwise(Handler<Matchable<M>, R> handler);
	}

	public interface ClosedPrototype<M, R> extends TypeMatcherFactory<M, R> {
		<MX extends M> ClosedPrototype<M, R> caseOf(Class<MX> matchType, Handler<Matchable<MX>, R> handler);
	}
	
	// contexted:

	public interface OpenContextedPrototype<M, C, R> extends Supplier<TypeMatcherFactory<C, Optional<R>>> {
		<MX extends M> OpenContextedPrototype<M, C, R> caseOf(Class<MX> matchType, Handler<ContextedMatchable<MX, C>, R> handler);
		TypeMatcherFactory<C, R> otherwise(Handler<ContextedMatchable<M, C>, R> handler);
	}

	public interface ClosedContextedPrototype<M, C, R> extends Supplier<TypeMatcherFactory<C, R>> {
		<MX extends M> ClosedContextedPrototype<M,C, R> caseOf(Class<MX> matchType, Handler<ContextedMatchable<MX, C>, R> handler);
	}
	
	// stateful:
	
	public interface OpenStatefulPrototype<M, S, R> extends Supplier<StatefulTypeMatcherFactory<M, S, Optional<R>>> {
		<MX extends M> OpenStatefulPrototype<M, S, R> caseOf(Class<MX> matchType, Handler<StatefulMatchable<MX, S>, R> handler);
		StatefulTypeMatcherFactory<M, S, R> otherwise(Handler<StatefulMatchable<M, S>, R> handler);
	}
	
	public interface ClosedStatefulPrototype<M, S, R> extends Supplier<StatefulTypeMatcherFactory<M, S, R>> {
		<MX extends M> ClosedStatefulPrototype<M, S, R> caseOf(Class<MX> matchType, Handler<StatefulMatchable<MX, S>, R> handler);
	}
	
	// stateful & contexted:
	
	public interface OpenStatefulContextedPrototype<M, C, S, R> extends Supplier<StatefulTypeMatcherFactory<C, S, Optional<R>>> {
		<MX extends M> OpenStatefulContextedPrototype<M, C, S, R> caseOf(Class<MX> matchType, Handler<StatefulContextedMatchable<MX, C, S>, R> handler);
		StatefulTypeMatcherFactory<C, S, R> otherwise(Handler<StatefulContextedMatchable<M, C, S>, R> handler);
	}	
	
	public interface ClosedStatefulContextedPrototype<M, C, S, R> extends Supplier<StatefulTypeMatcherFactory<C, S, R>> {
		<MX extends M> ClosedStatefulContextedPrototype<M, C, S, R> caseOf(Class<MX> matchType, Handler<StatefulContextedMatchable<MX, C, S>, R> handler);
	}
}
