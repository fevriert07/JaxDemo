package jaxdemo.eventhandler;

import com.google.common.eventbus.Subscribe;

public interface EventBusSubscriber<T> {
	
	@Subscribe
	public void handleEvent(T event);

}
