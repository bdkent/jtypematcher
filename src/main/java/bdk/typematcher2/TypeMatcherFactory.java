package bdk.typematcher2;

public interface TypeMatcherFactory<I, O> {

	public enum MatchRigor {
		STRICT, POLYMORPHIC
	}

	TypeMatcher<I, O> newStatelessTypeMatcher(MatchRigor rigor);

	<S> StatefulTypeMatcher<I, S, O> newStatefulTypeMatcher(MatchRigor rigor);

	<S> StatefulTypeMatcher<I, S, O> newStatefulTypeMatcher(MatchRigor rigor, Class<S> statefulType);
}
