package bdk.typematcher2;

import java.util.List;
import java8.util.Optional;

import bdk.typematcher2.TypeMatcherFactory.MatchRigor;
import bdk.typematcher2.TypeMatchers.ClosedDirectContext;
import bdk.typematcher2.TypeMatchers.ClosedIndirectContext;
import bdk.typematcher2.TypeMatchers.OpenDirectContext;
import bdk.typematcher2.TypeMatchers.OpenIndirectContext;

public class App {
	
	// strict vs polymorphic
	
	// stateful vs stateless

	interface Context {
	};

	static final Context CONTEXT = new Context() {
	};

	static class Number2String implements DirectContext.Handler<Number, String> {
		@Override
		public String handle(Number d) {
			return null;
		}
	}

	static class Double2String implements DirectContext.Handler<Double, String> {
		@Override
		public String handle(Double d) {
			return null;
		}
	}

	static class CNumber2String implements IndirectContext.Handler<Context, Number, String> {
		@Override
		public String handle(Context c, Number d) {
			return null;
		}
	}

	static class CDouble2String implements IndirectContext.Handler<Context, Double, String> {
		@Override
		public String handle(Context c, Double d) {
			return null;
		}
	}

	public static void main5(String[] args) {

		OpenDirectContext<Number, String> builder = TypeMatchers.newDirectContext(Number.class, String.class);

		builder.add(Double.class, new Double2String());

		builder.add(Number.class, new Number2String());

		StatefulTypeMatcher<Number, List<String>, Optional<String>> matcher = builder.build().<List<String>>newStatefulTypeMatcher(MatchRigor.STRICT);
		
		List<String> xs = null;

		matcher.match(Double.valueOf(1), xs);
		matcher.match((Number) Double.valueOf(1), xs);
	}

	public static void main4(String[] args) {

		KeyGenerator<Context, Number> keyGen = null;
		ClosedIndirectContext<Context, Number, String> builder = TypeMatchers.newIndirectContext(keyGen, new CNumber2String());

		builder.add(Double.class, new CDouble2String());

		TypeMatcher<Context, String> matcher = builder.build().newStatelessTypeMatcher(MatchRigor.STRICT);

		matcher.match(CONTEXT);
	}

	public static void main3(String[] args) {

		KeyGenerator<Context, Number> keyGen = null;

		OpenIndirectContext<Context, Number, String> builder = TypeMatchers.newIndirectContext(keyGen, String.class);

		builder.add(Double.class, new CDouble2String());

		TypeMatcher<Context, Optional<String>> matcher = builder.build().newStatelessTypeMatcher(MatchRigor.STRICT);

		matcher.match(CONTEXT);
	}

	public static void main2(String[] args) {

		ClosedDirectContext<Number, String> builder = TypeMatchers.newDirectContext(Number.class, new Number2String());

		builder.add(Double.class, new Double2String());

		TypeMatcher<Number, String> matcher = builder.build().newStatelessTypeMatcher(MatchRigor.STRICT);

		matcher.match(Double.valueOf(1));
		matcher.match(Long.valueOf(1));
	}

	public static void main(String[] args) {

		OpenDirectContext<Number, String> builder = TypeMatchers.newDirectContext(Number.class, String.class);

		builder.add(Double.class, new Double2String());
		builder.add(Number.class, new Number2String());

		TypeMatcher<Number, Optional<String>> matcher = builder.build().newStatelessTypeMatcher(MatchRigor.STRICT);

		matcher.match(Double.valueOf(1));
		matcher.match((Number) Double.valueOf(1));
	}
}
