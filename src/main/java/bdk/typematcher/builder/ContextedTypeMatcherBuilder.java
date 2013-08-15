package bdk.typematcher.builder;

import bdk.lang.Builder;
import bdk.typematcher.ContextedTypeMatcher;

public interface ContextedTypeMatcherBuilder<C, T, O, B> extends Builder<B> {

	<P extends T> ContextedTypeMatcherBuilder<C, T, O, B> add(Class<P> type, ContextedTypeMatcher<C, P, O> typeMatcher);
}
