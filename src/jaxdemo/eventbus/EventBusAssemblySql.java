package jaxdemo.eventbus;

import jaxdemo.eventhandler.PersonRecordEventSqlHandler;
import jaxdemo.events.PersonRecordEvent;
import jaxdemo.events.UploadDataEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusAssemblySql {

	private Logger log = LogManager.getLogger(this);
	private EventBus eventBus;
	private PersonRecordEventSqlHandler personRecordEventSqlHandler;

	public EventBusAssemblySql() {
		this.eventBus = new EventBus();
		eventBus.register(this);
		personRecordEventSqlHandler = new PersonRecordEventSqlHandler();
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	@Subscribe
	public void handlePersonRecordEvent(PersonRecordEvent event) {

		personRecordEventSqlHandler.handleEvent(event);

	}

	@Subscribe
	public void handleUploadDataEvent(UploadDataEvent event) {
		log.info("Received UploadDataEvent for " + event.getDataFileName());

		PersonRecordEvent personRecordEvent = new PersonRecordEvent();
		personRecordEvent.fillPersonRecordEvent("Henry", "J", "Applesouce", "201-12-1425", DateTime.parse("1995-05-14"));
		eventBus.post(personRecordEvent);
	}

}
