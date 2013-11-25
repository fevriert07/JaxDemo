package jax.java.jaxdemo.eventhandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import jax.java.jaxdemo.events.PersonRecordEvent;
import jax.java.jaxdemo.events.UploadDataEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import com.google.common.eventbus.EventBus;

public class UploadDataEventHandler implements EventHandler<UploadDataEvent>{
	
	private EventBus eventBus;
	private Map<String, String> memoryReducingStringMap = new HashMap<String, String>();
	private Map<String, DateTime> memoryReducingDateMap = new HashMap<String, DateTime>();
	private Logger log = LogManager.getLogger(this);
	
	public UploadDataEventHandler(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	@Override
	public void applyEvent(UploadDataEvent event) throws Exception{
		publishPersonRecordEvents(event.getDataFileName());
	}
	
	private void publishPersonRecordEvents(String dataFilename) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(dataFilename)));
		String line;
		int count = 0;
		while((line = br.readLine()) !=null){
			if(line.trim().length()>0){
				count++;
				String[] f = line.split(",");
				String firstName = getStringValue(f[0]);
				String middleInitial = getStringValue(f[1]);
				String lastName = getStringValue(f[2]);
				String ssn = getStringValue(f[3]);
				DateTime dob = getDateValue(f[4]);
				
				PersonRecordEvent personRecordEvent = new PersonRecordEvent(firstName, middleInitial, lastName, ssn, dob);
				if(count % 250000 ==0)
					log.info("Publishing event ("+ count+" ) :"+  personRecordEvent);
				eventBus.post(personRecordEvent);
			}
		}
	}
	
	private String getStringValue(String value){
		if(memoryReducingStringMap.containsKey(value)){
			return memoryReducingStringMap.get(value);
		}
		else{
			memoryReducingStringMap.put(value, value);
			return value;
		}
	}
	
	private DateTime getDateValue(String value){
		if(memoryReducingDateMap.containsKey(value)){
			return memoryReducingDateMap.get(value);
		}
		else{
			DateTime dateValue = DateTime.parse(value);
			memoryReducingDateMap.put(value, dateValue);
			return dateValue;
		}
	}
	
	
}
