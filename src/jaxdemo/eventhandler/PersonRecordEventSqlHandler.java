package jaxdemo.eventhandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jaxdemo.events.PersonRecordEvent;

public class PersonRecordEventSqlHandler{

	private Logger log = LogManager.getLogger(this);
	
	
	public void handleEvent(PersonRecordEvent event) {
		
		log.info("Received Person Event for "+event.getSocialSecurityNumber());
		
	}

}
