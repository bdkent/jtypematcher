package bdk.typematcher2;

public interface KeyGenerator<A,B> {

	B key(A a);
}
