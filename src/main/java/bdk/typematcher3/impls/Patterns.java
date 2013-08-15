package bdk.typematcher3.impls;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java8.util.Objects;

import bdk.typematcher3.Handler;
import bdk.typematcher3.Matches.Matchable;

public class Patterns<M,R> {
	private final Map<Class<?>, Handler<? extends Matchable<? extends M>, R>> entries = Collections.synchronizedMap(new LinkedHashMap<Class<?>, Handler<? extends Matchable<? extends M>, R>>());
	
	public <MX extends M> void caseOf(Class<MX> matchType, Handler<Matchable<MX>, R> handler) {
		Objects.requireNonNull(matchType);
		Objects.requireNonNull(handler);
		entries.put(matchType, handler);
	}
	
	public Map<Class<?>, Handler<Matchable<?>, R>> entries() {
		final Map<Class<?>, Handler<Matchable<?>, R>> map = new LinkedHashMap<Class<?>, Handler<Matchable<?>, R>>();
		synchronized (entries) {
			for (Entry<Class<?>, Handler<? extends Matchable<? extends M>, R>> e : entries.entrySet()) {
				@SuppressWarnings("unchecked")
				final Handler<Matchable<?>, R> value = (Handler<Matchable<?>, R>) e.getValue();
				map.put(e.getKey(), value);
			}
		}
		return Collections.unmodifiableMap(map);
	}
}