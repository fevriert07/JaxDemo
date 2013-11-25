package jax.java.jaxdemo.eventhandler;

import java.util.ArrayList;
import java.util.List;

import jax.java.jaxdemo.events.QueryDataEvent;
import jax.java.jaxdemo.view.PersonRecord;
import jax.java.jaxdemo.view.PersonRecordView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QueryEventHandler implements EventHandler<QueryDataEvent> {

	private PersonRecordView personRecordView;
	private Logger log = LogManager.getLogger(this);
	private static int MAX_PRINT_RECORDS = 5;

	public QueryEventHandler(PersonRecordView personRecordView) {
		this.personRecordView = personRecordView;
	}

	@Override
	public void applyEvent(QueryDataEvent event) throws Exception {
		List<PersonRecord> queryResults = new ArrayList<PersonRecord>();

		if (event.getQuerySsn() != null) {
			PersonRecord queryBySsnResults = personRecordView.getSsnIndex().get(event.getQuerySsn());
			if (queryBySsnResults != null) {
				queryResults.add(queryBySsnResults);
			}
		} else if (event.getQueryDate() != null) {
			List<PersonRecord> queryByDateResults = personRecordView.getDateIndex().get(event.getQueryDate());
			if (queryByDateResults != null) {
				queryResults.addAll(queryByDateResults);
			}
		} else {
			queryResults.addAll(personRecordView.getViewStore().values());
		}

		long queryTime = System.currentTimeMillis() - event.getQueryStartTime();
		log.info("Completed Query ssn=" + event.getQuerySsn() + " dob=" + event.getQueryDate() + " in " + queryTime + "ms (" + queryResults.size()
				+ " records)");

		int count = 0;
		for (PersonRecord p : queryResults) {
			count++;
			log.info(p.toString());
			if (count >= MAX_PRINT_RECORDS) {
				log.info((queryResults.size() - count) + " More Records...");
				break;
			}
		}

	}

}
