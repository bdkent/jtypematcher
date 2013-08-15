package java8.util;

import java.util.NoSuchElementException;

import java8.util.function.Supplier;

public abstract class Optional<T> {

	private static final Optional<?> ABSENT = new Absent<Object>();

	public static <T> Optional<T> of(T value) {
		return new Present<T>(value);
	}

	@SuppressWarnings("unchecked")
	public static <T> Optional<T> empty() {
		return (Optional<T>) ABSENT;
	}

	public static <T> Optional<T> ofNullable(T value) {
		if (value == null) {
			return empty();
		} else {
			return of(value);
		}
	}
	
	private Optional() {
		super();
	}

	public abstract T orElse(T other);

	public abstract boolean isPresent();

	public abstract T orElseGet(Supplier<? extends T> other);

	public abstract T get();

	private static final class Absent<T> extends Optional<T> {

		private Absent() {
			super();
		}

		@Override
		public T orElse(T other) {
			return other;
		}
		
		@Override
		public boolean isPresent() {
			return false;
		}
		
		@Override
		public T orElseGet(Supplier<? extends T> other) {
			return other.get();
		}
		
		@Override
		public T get() {
			throw new NoSuchElementException();
		}
	}

	private static final class Present<T> extends Optional<T> {

		private final T val;

		private Present(T val) {
			this.val = val;
		}

		@Override
		public T orElse(T other) {
			return val;
		}
		
		@Override
		public boolean isPresent() {
			return true;
		}
		
		@Override
		public T orElseGet(Supplier<? extends T> other) {
			return val;
		}
		
		@Override
		public T get() {
			return val;
		}
	}
}
