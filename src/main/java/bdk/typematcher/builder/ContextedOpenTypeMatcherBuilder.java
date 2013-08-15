package bdk.typematcher.builder;

import java8.util.Optional;

import bdk.typematcher.TypeMatcher;

public interface ContextedOpenTypeMatcherBuilder<C, T, O> extends ContextedTypeMatcherBuilder<C, T, O, TypeMatcher<C, Optional<O>>> {

}
