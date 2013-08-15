package bdk.typematcher.builder;

import java8.util.Optional;

import bdk.typematcher.TypeMatcher;

public interface ContextlessOpenTypeMatcherBuilder<T, O> extends ContextlessTypeMatcherBuilder<T, O, TypeMatcher<T, Optional<O>>> {

}
