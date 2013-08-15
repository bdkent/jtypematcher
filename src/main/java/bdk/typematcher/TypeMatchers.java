package bdk.typematcher;

import bdk.typematcher.builder.ContextedClosedTypeMatcherBuilder;
import bdk.typematcher.builder.ContextedOpenTypeMatcherBuilder;
import bdk.typematcher.builder.ContextlessClosedTypeMatcherBuilder;
import bdk.typematcher.builder.ContextlessOpenTypeMatcherBuilder;


public class TypeMatchers {
	
	public static <S, T, O> ContextedOpenTypeMatcherBuilder<S, T, O> newStrictTypeMatcherBuilder(Class<S> stateType, Class<T> matchType, Class<O> returnType) {
		return null;
	}

	public static <S, T, O> ContextedClosedTypeMatcherBuilder<S, T, O> newStrictTypeMatcherBuilder(Class<S> stateType, Class<T> type, ContextedTypeMatcher<S, T, O> handler) {
		return null;
	}

	public static <C, T, O> ContextedOpenTypeMatcherBuilder<C, T, O> newStrictTypeMatcherBuilder(KeyGenerator<C, T> keyGen, Class<T> matchType, Class<O> returnType) {
		return null;
	}

	public static <C, T, O> ContextedClosedTypeMatcherBuilder<C, T, O> newStrictTypeMatcherBuilder(KeyGenerator<C, T> keyGen, Class<T> type, ContextedTypeMatcher<C, T, O> handler) {
		return null;
	}

	public static <T, O> ContextlessOpenTypeMatcherBuilder<T, O> newStrictTypeMatcherBuilder(Class<T> matchType, Class<O> returnType) {
		return null;
	}

	public static <T, O> ContextlessClosedTypeMatcherBuilder<T, O> newStrictTypeMatcherBuilder(Class<T> type, TypeMatcher<T, O> handler) {
		return null;
	}
	
	
//
//	public static <T, O> TypeMatcherBuilder<T, Optional<O>> newPolymorphicTypeMatcherBuilder(Class<T> matchType, Class<O> returnType) {
//		return null;
//	}
//
//	public static <T, O> TypeMatcherBuilder<T, O> newPolymorphicTypeMatcherBuilder(Class<T> type, TypeMatcher<T, O> handler) {
//		return null;
//	}
//	
//	
//
//	public static <T, O> TypeMatcher<T, O> newUnsupportedOperationExceptionTypeMatcher(TypeMatcher<T, Optional<O>> matcher) {
//		return null;
//	}
}
