package jaxdemo.main;

import jaxdemo.eventbus.EventBusAssemblySql;
import jaxdemo.events.UploadDataEvent;

import com.google.common.eventbus.EventBus;

public class RunJaxDemo {

	public static void main(String[] args) throws Exception {
				
		
		EventBus eventBus = new EventBusAssemblySql().getEventBus();
		eventBus.post(new UploadDataEvent("testfile"));

	}

}
