package jax.java.jaxdemo.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import jax.java.jaxdemo.eventhandler.EventHandler;
import jax.java.jaxdemo.events.PersonRecordEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

public class PersonRecordView implements EventHandler<PersonRecordEvent> {
	private Map<String, PersonRecord> viewStore = new HashMap<String, PersonRecord>(1000000);
	private Map<String, PersonRecord> ssnIndex = new HashMap<String, PersonRecord>(1000000);
	private Map<DateTime, List<PersonRecord>> dateIndex = new HashMap<DateTime, List<PersonRecord>>(100000);
	private Map<String, String> idMap = new HashMap<String, String>();
	private Logger log = LogManager.getLogger(this);

	public Map<String, PersonRecord> getViewStore() {
		return viewStore;
	}

	public Map<String, PersonRecord> getSsnIndex() {
		return ssnIndex;
	}

	public Map<DateTime, List<PersonRecord>> getDateIndex() {
		return dateIndex;
	}

	private int currentId = 0;
	
	private AtomicInteger eventCount = new AtomicInteger(0);

	@Override
	public void applyEvent(PersonRecordEvent personRecordEvent) {

		String ssn = personRecordEvent.getSsn();
		DateTime dob = personRecordEvent.getDob();
		String id = getId(ssn);
		PersonRecord personRecord = new PersonRecord(id, personRecordEvent.getFirstName(), personRecordEvent.getMiddleInitial(),
				personRecordEvent.getLastName(), ssn, dob);

		PersonRecord previousPersonRecord = viewStore.get(id);
		viewStore.put(id, personRecord);
		ssnIndex.put(ssn, personRecord);
		removePreviousPersonFromDateIndex(previousPersonRecord);
		addToDateIndex(dob, personRecord);
		
		int currentEventCount = eventCount.incrementAndGet();
		if (currentEventCount % 250000 ==0){
			log.info("Processed event ("+currentEventCount+"): "+ personRecordEvent);
		}

	}

	private void removePreviousPersonFromDateIndex(PersonRecord previousPersonRecord) {
		if (previousPersonRecord != null && dateIndex.containsKey(previousPersonRecord.getDob())) {
			dateIndex.get(previousPersonRecord.getDob()).remove(previousPersonRecord);
		}
	}

	private void addToDateIndex(DateTime dob, PersonRecord personRecord) {
		List<PersonRecord> personRecordByDate = dateIndex.get(dob);
		if (personRecordByDate == null) {
			personRecordByDate = new ArrayList<PersonRecord>();
			dateIndex.put(dob, personRecordByDate);
		}
		personRecordByDate.add(personRecord);
	}

	synchronized private String getId(String ssn) {

		String id = idMap.get(ssn);
		if (id == null) {
			currentId++;
			id = Integer.toString(currentId);
			idMap.put(ssn, id);
		}

		return id;

	}

}
