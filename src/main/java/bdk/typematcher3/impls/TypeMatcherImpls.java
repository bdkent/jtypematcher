package bdk.typematcher3.impls;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java8.util.Objects;
import java8.util.Optional;

import bdk.typematcher3.Handler;
import bdk.typematcher3.Matches;
import bdk.typematcher3.Matches.Matchable;
import bdk.typematcher3.TypeMatcher;

public class TypeMatcherImpls {

	public static <M, R> TypeMatcher<M, R> newStrictTypeMatcher(final Patterns<M, R> patterns) {

		Objects.requireNonNull(patterns);

		final Map<Class<?>, Handler<Matchable<?>, R>> patternMap = patterns.entries();

		class StrictTypeMatcher implements TypeMatcher<M, R> {
			public R match(M m) {
				final Handler<Matchable<?>, R> handler = patternMap.get(m.getClass());
				if (handler == null) {
					return null;
				} else {
					return handler.handle(Matches.toMatchable(m));
				}
			}
		}
		return new StrictTypeMatcher();
	}

	public static <M, R> TypeMatcher<M, R> newPolymorphicTypeMatcher(final Patterns<M, R> patterns) {

		Objects.requireNonNull(patterns);

		final Map<Class<?>, Handler<Matchable<?>, R>> patternMap = patterns.entries();

		class PolymorphicTypeMatcher implements TypeMatcher<M, R> {

			private final Map<Class<?>, Handler<Matchable<?>, R>> cache = new WeakHashMap<Class<?>, Handler<Matchable<?>, R>>();
			private final ReadWriteLock lock = new ReentrantReadWriteLock();
			private final Lock readLock = lock.readLock();
			private final Lock writeLock = lock.writeLock();

			public R match(M m) {

				final Class<?> key = m.getClass();

				final Handler<Matchable<?>, R> handler;
				readLock.lock();
				try {
					handler = cache.get(key);
				} finally {
					readLock.unlock();
				}

				if (handler != null) {
					return handler.handle(Matches.toMatchable(m));
				} else {
					for (Class<?> k : patternMap.keySet()) {
						if (k.isAssignableFrom(key)) {

							writeLock.lock();
							try {
								cache.put(key, patternMap.get(k));
							} finally {
								writeLock.unlock();
							}

							return match(m);
						}
					}

					return null;
				}
			}
		}
		return new PolymorphicTypeMatcher();
	}

	public static <M, R> TypeMatcher<M, Optional<R>> newTypeMatcher(final TypeMatcher<M, R> typeMatcher) {
		Objects.requireNonNull(typeMatcher);

		return new TypeMatcher<M, Optional<R>>() {
			public Optional<R> match(M m) {
				return Optional.ofNullable(typeMatcher.match(m));
			}
		};
	}
}
