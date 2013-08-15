package bdk.typematcher;

public interface KeyGenerator<A,B> {

	B key(A a);
}
