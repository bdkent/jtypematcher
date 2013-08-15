package bdk.typematcher3;

import java.util.Collections;
import java.util.List;
import java8.util.Objects;
import java8.util.Optional;

import bdk.typematcher3.Factories.MatchRigor;
import bdk.typematcher3.Factories.TypeMatcherFactory;
import bdk.typematcher3.Matches.ContextedMatchable;
import bdk.typematcher3.Matches.Matchable;
import bdk.typematcher3.Matches.StatefulContextedMatchable;
import bdk.typematcher3.Matches.StatefulMatchable;
import bdk.typematcher3.Prototypes.ClosedContextedPrototype;
import bdk.typematcher3.Prototypes.ClosedPrototype;
import bdk.typematcher3.Prototypes.ClosedStatefulContextedPrototype;
import bdk.typematcher3.Prototypes.ClosedStatefulPrototype;
import bdk.typematcher3.Prototypes.OpenContextedPrototype;
import bdk.typematcher3.Prototypes.OpenPrototype;
import bdk.typematcher3.Prototypes.OpenStatefulContextedPrototype;
import bdk.typematcher3.Prototypes.OpenStatefulPrototype;
import bdk.typematcher3.impls.Patterns;
import bdk.typematcher3.impls.TypeMatcherImpls;

public class TypeMatchers {

	// direct:

	public static <M, R> OpenPrototype<M, R> newPrototype(Class<M> matchType, Class<R> returnType) {
		Objects.requireNonNull(matchType);
		Objects.requireNonNull(returnType);

		class Prototype implements OpenPrototype<M, R> {

			final Patterns<M, R> patterns = new Patterns<M, R>();

			@Override
			public <MX extends M> OpenPrototype<M, R> caseOf(Class<MX> matchType, Handler<Matchable<MX>, R> handler) {
				patterns.caseOf(matchType, handler);
				return this;
			}

			@Override
			public TypeMatcherFactory<M, R> otherwise(final Handler<Matchable<M>, R> handler) {
				Objects.requireNonNull(handler);

				// TODO: not safe if other caseOf called by old ref
				final TypeMatcherFactory<M, Optional<R>> factory = this;

				return new TypeMatcherFactory<M, R>() {
					@Override
					public TypeMatcher<M, R> newTypeMatcher(MatchRigor rigor) {
						Objects.requireNonNull(rigor);

						final TypeMatcher<M, Optional<R>> typeMatcher = factory.newTypeMatcher(rigor);
						final TypeMatcher<M, R> otherwise = TypeMatchers.newTypeMatcher(handler);
						return TypeMatchers.newTypeMatcher(Collections.singletonList(typeMatcher), otherwise);
					}
				};
			}

			@Override
			public TypeMatcher<M, Optional<R>> newTypeMatcher(MatchRigor rigor) {
				return newOpenTypeMatcher(rigor, patterns);
			}
		}

		return new Prototype();
	}

	public static <M, R> ClosedPrototype<M, R> newPrototype(final Class<M> matchType, final Handler<Matchable<M>, R> handler) {
		Objects.requireNonNull(matchType);
		Objects.requireNonNull(handler);

		final Patterns<M, R> patterns = new Patterns<M, R>();

		class Prototype implements ClosedPrototype<M, R> {
			@Override
			public <MX extends M> ClosedPrototype<M, R> caseOf(Class<MX> matchType, Handler<Matchable<MX>, R> handler) {
				patterns.caseOf(matchType, handler);
				return this;
			}

			@Override
			public TypeMatcher<M, R> newTypeMatcher(MatchRigor rigor) {
				patterns.caseOf(matchType, handler);
				return newClosedTypeMatcher(rigor, patterns);
			}
		}

		return new Prototype();
	}

	// context:

	public static <M, C, R> OpenContextedPrototype<M, C, R> newPrototype(MatchMapper<C, M> mapper, Class<R> returnType) {
		return null;
	}

	public static <M, C, R> ClosedContextedPrototype<M, C, R> newPrototype(MatchMapper<C, M> mapper, Handler<ContextedMatchable<M, C>, R> handler) {
		return null;
	}

	// state:

	public static <M, S, R> OpenStatefulPrototype<M, S, R> newPrototype(Class<M> matchType, Class<S> stateType, Class<R> returnType) {
		return null;
	}

	public static <M, S, R> ClosedStatefulPrototype<M, S, R> newPrototype(Class<M> matchType, Class<S> stateType, Handler<StatefulMatchable<M, S>, R> handler) {
		return null;
	}

	// state:

	public static <M, C, S, R> OpenStatefulContextedPrototype<M, C, S, R> newPrototype(MatchMapper<C, M> mapper, Class<S> stateType, Class<R> returnType) {
		return null;
	}

	public static <M, C, S, R> ClosedStatefulContextedPrototype<M, C, S, R> newPrototype(MatchMapper<C, M> mapper, Class<S> stateType, Handler<StatefulContextedMatchable<M, C, S>, R> handler) {
		return null;
	}

	// transforms:

	public static <M, R> TypeMatcher<M, R> newTypeMatcher(final Handler<Matchable<M>, R> handler) {
		Objects.requireNonNull(handler);

		return new TypeMatcher<M, R>() {
			public R match(M m) {
				return handler.handle(Matches.toMatchable(m));
			}
		};
	}

	public static <M, R> TypeMatcher<M, Optional<R>> newTypeMatcher(final List<TypeMatcher<M, Optional<R>>> cases) {
		Objects.requireNonNull(cases);

		return new TypeMatcher<M, Optional<R>>() {
			public Optional<R> match(M m) {
				Objects.requireNonNull(m);

				for (TypeMatcher<M, Optional<R>> matcher : cases) {
					final Optional<R> opt = matcher.match(m);
					if (opt.isPresent()) {
						return opt;
					}
				}

				return Optional.empty();
			}
		};
	}

	public static <M, R> TypeMatcher<M, R> newTypeMatcher(final List<TypeMatcher<M, Optional<R>>> cases, final TypeMatcher<M, R> otherwise) {
		Objects.requireNonNull(cases);

		final TypeMatcher<M, Optional<R>> casesMatcher = newTypeMatcher(cases);

		return new TypeMatcher<M, R>() {
			public R match(M m) {
				Objects.requireNonNull(m);

				final Optional<R> opt = casesMatcher.match(m);
				if (opt.isPresent()) {
					return opt.get();
				} else {
					return otherwise.match(m);
				}
			}
		};
	}
	
	
	
	static<M,R> TypeMatcher<M, R> newClosedTypeMatcher(MatchRigor rigor, Patterns <M,R> patterns) {
		Objects.requireNonNull(rigor);
		Objects.requireNonNull(patterns);

		switch (rigor) {
		case STRICT:
			return TypeMatcherImpls.newStrictTypeMatcher(patterns);
		case POLYMORPHIC:
			return TypeMatcherImpls.newPolymorphicTypeMatcher(patterns);
		default:
			throw new UnsupportedOperationException(rigor.toString());
		}
	}
	
	static<M,R> TypeMatcher<M, Optional<R>> newOpenTypeMatcher(MatchRigor rigor, Patterns <M,R> patterns) {
		Objects.requireNonNull(rigor);
		Objects.requireNonNull(patterns);
		
		return TypeMatcherImpls.newTypeMatcher(newClosedTypeMatcher(rigor, patterns));
	}
}
