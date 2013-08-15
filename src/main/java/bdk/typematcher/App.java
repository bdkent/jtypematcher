package bdk.typematcher;

import java8.util.Optional;

import bdk.typematcher.builder.ContextedClosedTypeMatcherBuilder;
import bdk.typematcher.builder.ContextedOpenTypeMatcherBuilder;
import bdk.typematcher.builder.ContextlessClosedTypeMatcherBuilder;
import bdk.typematcher.builder.ContextlessOpenTypeMatcherBuilder;

public class App {
	
	// Simple / Context / State

	interface Context {
	};

	static final Context CONTEXT = new Context() {
	};

	static class Number2String implements TypeMatcher<Number, String> {
		@Override
		public String handle(Number d) {
			return null;
		}
	}

	static class Double2String implements TypeMatcher<Double, String> {
		@Override
		public String handle(Double d) {
			return null;
		}
	}

	static class CNumber2String implements ContextedTypeMatcher<Context, Number, String> {
		@Override
		public String handle(Context c, Number d) {
			return null;
		}
	}

	static class CDouble2String implements ContextedTypeMatcher<Context, Double, String> {
		@Override
		public String handle(Context c, Double d) {
			return null;
		}
	}

	public static void main4(String[] args) {

		KeyGenerator<Context, Number> keyGen = null;
		ContextedClosedTypeMatcherBuilder<Context, Number, String> builder = TypeMatchers.newStrictTypeMatcherBuilder(keyGen, Number.class, new CNumber2String());

		ContextedTypeMatcher<Context, Double, String> handler = new CDouble2String();

		builder.add(Double.class, handler);

		TypeMatcher<Context, String> matcher = builder.build();

		matcher.handle(CONTEXT);
	}

	public static void main3(String[] args) {

		KeyGenerator<Context, Number> keyGen = null;

		ContextedOpenTypeMatcherBuilder<Context, Number, String> builder = TypeMatchers.newStrictTypeMatcherBuilder(keyGen, Number.class, String.class);

		builder.add(Double.class, new CDouble2String());

		TypeMatcher<Context, Optional<String>> matcher = builder.build();

		matcher.handle(CONTEXT);
	}

	public static void main2(String[] args) {

		ContextlessClosedTypeMatcherBuilder<Number, String> builder = TypeMatchers.newStrictTypeMatcherBuilder(Number.class, new Number2String());

		TypeMatcher<Double, String> handler = new Double2String();

		builder.add(Double.class, handler);

		TypeMatcher<Number, String> matcher = builder.build();

		matcher.handle(Double.valueOf(1));
		matcher.handle(Long.valueOf(1));
	}

	public static void main(String[] args) {

		ContextlessOpenTypeMatcherBuilder<Number, String> builder = TypeMatchers.newStrictTypeMatcherBuilder(Number.class, String.class);

		builder.add(Double.class, new Double2String());

		builder.add(Number.class, new Number2String());

		TypeMatcher<Number, Optional<String>> matcher = builder.build();

		matcher.handle(Double.valueOf(1));
		matcher.handle((Number) Double.valueOf(1));
	}

}
