package jax.java.jaxdemo.eventhandler;

public interface EventHandler<T> {
	public void applyEvent(T event) throws Exception;
}
