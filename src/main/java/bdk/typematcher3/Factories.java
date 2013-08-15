package bdk.typematcher3;

public class Factories {

	public enum MatchRigor {
		STRICT, POLYMORPHIC
	}

	public interface TypeMatcherFactory<A, B> {
		TypeMatcher<A, B> newTypeMatcher(MatchRigor rigor);
	}

	public interface StatefulTypeMatcherFactory<A, S, B> {
		StatefulTypeMatcher<A, S, B> newTypeMatcher(MatchRigor rigor);
	}
}
