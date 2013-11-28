package jax.java.jaxdemo.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.joda.time.DateTime;

import jax.java.jaxdemo.eventbus.EventBusAssembly;
import jax.java.jaxdemo.events.QueryDataEvent;
import jax.java.jaxdemo.events.UploadDataEvent;

import com.google.common.eventbus.EventBus;

public class RunJaxDemo {

	public static void main(String[] args) throws Exception {

		EventBus eventBus = new EventBusAssembly().getEventBus();
		eventBus.post(new UploadDataEvent("insertDataFile"));

		InputStreamReader istream = new InputStreamReader(System.in);
		BufferedReader bufReader = new BufferedReader(istream);

		while (true) {
			System.out.println("**************************************");
			System.out.println("Menu");
			System.out.println("I: Load insert data file");
			System.out.println("U: Load update data file");
			System.out.println("0: Query All");
			System.out.println("1: Query all dates in 1990");
			System.out.println("2 <SSN>: Query by SSN");
			System.out.println("3 <YYYY-MM-DD>: Query by Date");

			System.out.print("Choose --->");
			String choice = bufReader.readLine().toUpperCase();

			if ("I".equals(choice)) {
				eventBus.post(new UploadDataEvent("insertDataFile"));
			} else if ("U".equals(choice)) {
				eventBus.post(new UploadDataEvent("updateDataFile"));
			} else if ("0".equals(choice)) {
				eventBus.post(new QueryDataEvent());
			} else if ("1".equals(choice)) {
				queryDateRange(eventBus);
			} else if (choice.startsWith("2")) {
				try {
					String querySsn = choice.split("\\s")[1].trim();
					eventBus.post(new QueryDataEvent(querySsn));
				} catch (Exception e) {
					System.out.println("SSN required for query");
				}
			} else if (choice.startsWith("3")) {
				try {
					DateTime queryDate = DateTime.parse(choice.split("\\s")[1].trim());
					eventBus.post(new QueryDataEvent(queryDate));
				} catch (Exception e) {
					System.out.println("Date required for query");
				}
			}
			else{
				System.out.println("Invalid Choice - try again");
			}
			

		}

	}

	private static void queryDateRange(EventBus eventBus) {
		DateTime startDate = DateTime.parse("1990-01-01");
		DateTime endDate = DateTime.parse("1990-12-31");

		do {
			eventBus.post(new QueryDataEvent(startDate, null));
			startDate = startDate.plusDays(1);
		} while (startDate.compareTo(endDate) <= 0);
	}

}