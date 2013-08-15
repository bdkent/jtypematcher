package bdk.typematcher2;

public interface DirectContext<T,O> {
	
	public interface Handler<T,O> {
		O handle(T type); 
	}

	<P extends T> DirectContext<T,O> add(Class<P> type, Handler<P, O> handler);
}
