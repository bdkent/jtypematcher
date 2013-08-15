package bdk.typematcher3;

import java8.lang.FunctionalInterface;

import bdk.typematcher3.Matches.Matchable;

@FunctionalInterface
public interface Handler<A extends Matchable<?>, B> {
	B handle(A a);
}
