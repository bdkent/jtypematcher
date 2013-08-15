package bdk.typematcher.builder;

import bdk.typematcher.TypeMatcher;

public interface ContextlessTypeMatcherBuilder<T, O, B> extends ContextedTypeMatcherBuilder<T, T, O, B> {

	<P extends T> ContextlessTypeMatcherBuilder<T, O, B> add(Class<P> type, TypeMatcher<P, O> typeMatcher);
}
