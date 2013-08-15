package bdk.typematcher2;


public interface IndirectContext<C, T, O> {
	
	public interface Handler<C, T, O> {
		O handle(C context, T type);
	}

	<P extends T> IndirectContext<C, T, O> add(Class<P> type, Handler<C, P, O> typeMatcher);
}
