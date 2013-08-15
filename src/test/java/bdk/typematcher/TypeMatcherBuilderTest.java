package bdk.typematcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java8.util.Optional;

import org.junit.Test;

import bdk.typematcher3.Factories.MatchRigor;
import bdk.typematcher3.Factories.TypeMatcherFactory;
import bdk.typematcher3.Handler;
import bdk.typematcher3.Matches.Matchable;
import bdk.typematcher3.Prototypes.ClosedPrototype;
import bdk.typematcher3.Prototypes.OpenPrototype;
import bdk.typematcher3.TypeMatcher;
import bdk.typematcher3.TypeMatchers;

public class TypeMatcherBuilderTest {

	

	class Number2String implements Handler<Matchable<Number>, String> {
		@Override
		public String handle(Matchable<Number> m) {
			return "NUMBER: " + m.getMatch().toString();
		}
	}

	class Double2String implements Handler<Matchable<Double>, String> {
		@Override
		public String handle(Matchable<Double> m) {
			return "DOUBLE: " + m.getMatch().toString();
		}
	}
	
	
	@Test
	public void test_ClosedPrototype() {
		
		ClosedPrototype<Number, String> prototype = TypeMatchers.newPrototype(Number.class, new Number2String());
		assertNotNull(prototype);
		
		prototype.caseOf(Double.class, new Double2String());
		
		TypeMatcherFactory<Number, String> factory = prototype;

		TypeMatcher<Number, String> strictMatcher = factory.newTypeMatcher(MatchRigor.STRICT);
		assertNotNull(strictMatcher);

		assertEquals("DOUBLE: 1.0", strictMatcher.match(Double.valueOf(1)));
		assertEquals("NUMBER: 3", strictMatcher.match(Long.valueOf(3)));
		
		TypeMatcher<Number, String> polyMatcher = factory.newTypeMatcher(MatchRigor.POLYMORPHIC);
		assertNotNull(polyMatcher);
		
		assertEquals("DOUBLE: 1.0", polyMatcher.match(Double.valueOf(1)));
		assertEquals("NUMBER: 3", polyMatcher.match(Long.valueOf(3)));
	}
	
	@Test
	public void test_OpenPrototype() {

		OpenPrototype<Number, String> prototype = TypeMatchers.newPrototype(Number.class, String.class);
		assertNotNull(prototype);

		prototype.caseOf(Double.class, new Double2String());
		prototype.caseOf(Number.class, new Number2String());
		TypeMatcherFactory<Number, Optional<String>> factory = prototype;

		TypeMatcher<Number, Optional<String>> strictMatcher = factory.newTypeMatcher(MatchRigor.STRICT);
		assertNotNull(strictMatcher);

		assertEquals("DOUBLE: 1.0", strictMatcher.match(Double.valueOf(1)).orElse("<ABSENT>"));
		assertEquals("<ABSENT>", strictMatcher.match(Long.valueOf(3)).orElse("<ABSENT>"));
		
		TypeMatcher<Number, Optional<String>> polyMatcher = factory.newTypeMatcher(MatchRigor.POLYMORPHIC);
		assertNotNull(polyMatcher);
		
		assertEquals("DOUBLE: 1.0", polyMatcher.match(Double.valueOf(1)).orElse("<ABSENT>"));
		assertEquals("NUMBER: 3", polyMatcher.match(Long.valueOf(3)).orElse("<ABSENT>"));
	}
}
