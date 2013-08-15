package bdk.typematcher3;

import java8.util.Optional;

import bdk.typematcher3.Factories.MatchRigor;
import bdk.typematcher3.Matches.ContextedMatchable;
import bdk.typematcher3.Matches.Matchable;
import bdk.typematcher3.Matches.StatefulContextedMatchable;
import bdk.typematcher3.Matches.StatefulMatchable;
import bdk.typematcher3.Prototypes.ClosedContextedPrototype;
import bdk.typematcher3.Prototypes.ClosedPrototype;
import bdk.typematcher3.Prototypes.ClosedStatefulContextedPrototype;
import bdk.typematcher3.Prototypes.ClosedStatefulPrototype;
import bdk.typematcher3.Prototypes.OpenContextedPrototype;
import bdk.typematcher3.Prototypes.OpenPrototype;
import bdk.typematcher3.Prototypes.OpenStatefulContextedPrototype;
import bdk.typematcher3.Prototypes.OpenStatefulPrototype;

public class App {

	// strict vs polymorphic

	// stateful vs stateless

	interface Context {
	};

	static final Context CONTEXT = new Context() {
	};

	interface State {
	};

	static final State STATE = new State() {
	};

	static class Number2String implements Handler<Matchable<Number>, String> {
		@Override
		public String handle(Matchable<Number> m) {
			return null;
		}
	}

	static class Double2String implements Handler<Matchable<Double>, String> {
		@Override
		public String handle(Matchable<Double> d) {
			return null;
		}
	}

	static class CNumber2String implements Handler<ContextedMatchable<Number, Context>, String> {
		@Override
		public String handle(ContextedMatchable<Number, Context> cm) {
			return null;
		}
	}

	static class CDouble2String implements Handler<ContextedMatchable<Double, Context>, String> {
		@Override
		public String handle(ContextedMatchable<Double, Context> cm) {
			return null;
		}
	}

	public static void main8(String[] args) {

		MatchMapper<Context, Number> keyGen = null;

		ClosedStatefulContextedPrototype<Number, Context, State, String> builder = TypeMatchers.newPrototype(keyGen, State.class, new Handler<StatefulContextedMatchable<Number, Context, State>, String>() {
			@Override
			public String handle(StatefulContextedMatchable<Number, Context, State> scm) {
				return null;
			}
		});

		builder.caseOf(Double.class, new Handler<StatefulContextedMatchable<Double, Context, State>, String>() {
			@Override
			public String handle(StatefulContextedMatchable<Double, Context, State> scm) {
				return null;
			}
		});

		builder.caseOf(Number.class, new Handler<StatefulContextedMatchable<Number, Context, State>, String>() {
			@Override
			public String handle(StatefulContextedMatchable<Number, Context, State> scm) {
				return null;
			}
		});

		StatefulTypeMatcher<Context, State, String> matcher = builder.get().newTypeMatcher(MatchRigor.STRICT);

		matcher.match(CONTEXT, STATE);
		matcher.match(CONTEXT, STATE);
	}

	public static void main7(String[] args) {

		MatchMapper<Context, Number> keyGen = null;

		OpenStatefulContextedPrototype<Number, Context, State, String> builder = TypeMatchers.newPrototype(keyGen, State.class, String.class);

		builder.caseOf(Double.class, new Handler<StatefulContextedMatchable<Double, Context, State>, String>() {
			@Override
			public String handle(StatefulContextedMatchable<Double, Context, State> scm) {
				return null;
			}
		});

		builder.caseOf(Number.class, new Handler<StatefulContextedMatchable<Number, Context, State>, String>() {
			@Override
			public String handle(StatefulContextedMatchable<Number, Context, State> scm) {
				return null;
			}
		});

		StatefulTypeMatcher<Context, State, Optional<String>> matcher = builder.get().newTypeMatcher(MatchRigor.STRICT);

		matcher.match(CONTEXT, STATE);
		matcher.match(CONTEXT, STATE);
	}

	public static void main6(String[] args) {

		ClosedStatefulPrototype<Number, State, String> builder = TypeMatchers.newPrototype(Number.class, State.class, new Handler<StatefulMatchable<Number, State>, String>() {
			@Override
			public String handle(StatefulMatchable<Number, State> sm) {
				return null;
			}
		});

		builder.caseOf(Double.class, new Handler<StatefulMatchable<Double, State>, String>() {
			@Override
			public String handle(StatefulMatchable<Double, State> m) {
				return null;
			}
		});

		builder.caseOf(Number.class, new Handler<StatefulMatchable<Number, State>, String>() {
			@Override
			public String handle(StatefulMatchable<Number, State> m) {
				return null;
			}
		});

		StatefulTypeMatcher<Number, State, String> matcher = builder.get().newTypeMatcher(MatchRigor.STRICT);

		matcher.match(Double.valueOf(1), STATE);
		matcher.match((Number) Double.valueOf(1), STATE);
	}

	public static void main5(String[] args) {

		OpenStatefulPrototype<Number, State, String> builder = TypeMatchers.newPrototype(Number.class, State.class, String.class);

		builder.caseOf(Double.class, new Handler<StatefulMatchable<Double, State>, String>() {
			@Override
			public String handle(StatefulMatchable<Double, State> m) {
				return null;
			}
		});

		builder.caseOf(Number.class, new Handler<StatefulMatchable<Number, State>, String>() {
			@Override
			public String handle(StatefulMatchable<Number, State> m) {
				return null;
			}
		});

		StatefulTypeMatcher<Number, State, Optional<String>> matcher = builder.get().newTypeMatcher(MatchRigor.STRICT);

		matcher.match(Double.valueOf(1), STATE);
		matcher.match((Number) Double.valueOf(1), STATE);
	}

	public static void main4(String[] args) {

		MatchMapper<Context, Number> keyGen = null;
		ClosedContextedPrototype<Number, Context, String> builder = TypeMatchers.newPrototype(keyGen, new CNumber2String());

		builder.caseOf(Double.class, new CDouble2String());

		TypeMatcher<Context, String> matcher = builder.get().newTypeMatcher(MatchRigor.STRICT);

		matcher.match(CONTEXT);
	}

	public static void main3(String[] args) {

		MatchMapper<Context, Number> mapper = null;

		OpenContextedPrototype<Number, Context, String> builder = TypeMatchers.newPrototype(mapper, String.class);

		builder.caseOf(Double.class, new CDouble2String());

		TypeMatcher<Context, Optional<String>> matcher = builder.get().newTypeMatcher(MatchRigor.STRICT);

		matcher.match(CONTEXT);
	}

	public static void main2(String[] args) {

		ClosedPrototype<Number, String> builder = TypeMatchers.newPrototype(Number.class, new Number2String());

		builder.caseOf(Double.class, new Double2String());

		TypeMatcher<Number, String> matcher = builder.newTypeMatcher(MatchRigor.STRICT);

		matcher.match(Double.valueOf(1));
		matcher.match(Long.valueOf(1));
	}

	public static void main1(String[] args) {

		OpenPrototype<Number, String> prototype = TypeMatchers.newPrototype(Number.class, String.class);

		prototype.caseOf(Double.class, new Double2String());
		prototype.caseOf(Number.class, new Number2String());

		TypeMatcher<Number, Optional<String>> matcher = prototype.newTypeMatcher(MatchRigor.STRICT);

		matcher.match(Double.valueOf(1));
		matcher.match((Number) Double.valueOf(1));
	}
}
