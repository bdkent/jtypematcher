package bdk.typematcher2;

import java.util.HashMap;
import java.util.Map;
import java8.util.Objects;
import java8.util.Optional;

public class TypeMatchers {

	interface OpenDirectContext<I, O> extends DirectContext<I, O>, OpenBuilder<I, O> {
	}

	interface ClosedDirectContext<I, O> extends DirectContext<I, O>, ClosedBuilder<I, O> {
	}

	interface OpenIndirectContext<C, I, O> extends IndirectContext<C, I, O>, OpenBuilder<C, O> {
	}

	interface ClosedIndirectContext<C, I, O> extends IndirectContext<C, I, O>, ClosedBuilder<C, O> {
	}

	public static <I, O> OpenDirectContext<I, O> newDirectContext(Class<I> type, Class<O> returnType) {
		Objects.requireNonNull(type);
		Objects.requireNonNull(returnType);

		class Context implements OpenDirectContext<I, O> {

			final Map<Class<?>, DirectContext.Handler<? super I, O>> entries = new HashMap<Class<?>, DirectContext.Handler<? super I, O>>();

			@SuppressWarnings("unchecked")
			@Override
			public <P extends I> DirectContext<I, O> add(Class<P> type, DirectContext.Handler<P, O> handler) {

				Objects.requireNonNull(type);
				Objects.requireNonNull(handler);

				entries.put(type, (DirectContext.Handler<? super I, O>) handler);
				return this;
			}

			@Override
			public TypeMatcherFactory<I, Optional<O>> build() {

				class Factory implements TypeMatcherFactory<I, Optional<O>> {
					@Override
					public TypeMatcher<I, Optional<O>> newStatelessTypeMatcher(TypeMatcherFactory.MatchRigor rigor) {

						Objects.requireNonNull(rigor);

						switch (rigor) {
						case STRICT:

							class Matcher implements TypeMatcher<I, Optional<O>> {

								public Optional<O> match(I input) {
									Objects.requireNonNull(input);

									final DirectContext.Handler<? super I, O> handler = entries.get(input.getClass());

									if (handler == null) {
										return Optional.empty();
									} else {
										return Optional.of(handler.handle(input));
									}
								}
							}

							return new Matcher();
						case POLYMORPHIC:

							return null;

						default:
							throw new UnsupportedOperationException("unknown MatchRigor: " + rigor);
						}
					}

					@Override
					public <S> StatefulTypeMatcher<I, S, Optional<O>> newStatefulTypeMatcher(TypeMatcherFactory.MatchRigor rigor) {
						Objects.requireNonNull(rigor);

						switch (rigor) {
						case STRICT:

							class Matcher implements StatefulTypeMatcher<I, S, Optional<O>> {
								
								public Optional<O> match(I input, S state) {

									Objects.requireNonNull(input);

									final DirectContext.Handler<? super I, O> handler = entries.get(input.getClass());

									if (handler == null) {
										return Optional.empty();
									} else {
										return Optional.of(handler.handle(input));
									}
								}
							}

							return new Matcher();
						case POLYMORPHIC:

							return null;

						default:
							throw new UnsupportedOperationException("unknown MatchRigor: " + rigor);
						}
					}

					@Override
					public <S> StatefulTypeMatcher<I, S, Optional<O>> newStatefulTypeMatcher(TypeMatcherFactory.MatchRigor rigor, Class<S> statefulType) {
						return null;
					}
				}

				return new Factory();
			}
		}

		return new Context();
	}

	public static <I, O> ClosedDirectContext<I, O> newDirectContext(Class<I> type, DirectContext.Handler<I, O> handler) {
		return null;
	}

	public static <C, I, O> OpenIndirectContext<C, I, O> newIndirectContext(KeyGenerator<C, I> keyGen, Class<O> returnType) {
		return null;
	}

	public static <C, I, O> ClosedIndirectContext<C, I, O> newIndirectContext(KeyGenerator<C, I> keyGen, IndirectContext.Handler<C, I, O> handler) {
		return null;
	}
}
