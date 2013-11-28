package jax.java.jaxdemo.eventbus;

import jax.java.jaxdemo.eventhandler.QueryEventHandler;
import jax.java.jaxdemo.eventhandler.UploadDataEventHandler;
import jax.java.jaxdemo.events.PersonRecordEvent;
import jax.java.jaxdemo.events.QueryDataEvent;
import jax.java.jaxdemo.events.UploadDataEvent;
import jax.java.jaxdemo.view.PersonRecordView;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusAssembly {

	private EventBus eventBus;
	private PersonRecordView personRecordView;
	private UploadDataEventHandler uploadDataEventHandler;
	private QueryEventHandler queryEventHandler;

	public EventBusAssembly() throws Exception {
		this.eventBus = new EventBus(); //new AsyncEventBus(Executors.newFixedThreadPool(1));
		eventBus.register(this);		
		personRecordView = new PersonRecordView();
		uploadDataEventHandler = new UploadDataEventHandler(eventBus);
		queryEventHandler = new QueryEventHandler(personRecordView);
	}
		
	public EventBus getEventBus() {
		return eventBus;
	}

	@Subscribe
	public void handlePersonRecordEvent(PersonRecordEvent event){
		personRecordView.applyEvent(event);
	}
	
	@Subscribe
	public void handleUploadDateEvent(UploadDataEvent event) throws Exception{
		uploadDataEventHandler.applyEvent(event);
	}
	
	@Subscribe
	public void handleQueryDataEvent(QueryDataEvent event) throws Exception{
		queryEventHandler.applyEvent(event);
		
	}

}
