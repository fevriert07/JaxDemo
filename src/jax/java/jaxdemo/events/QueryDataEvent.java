package jax.java.jaxdemo.events;

import org.joda.time.DateTime;

public class QueryDataEvent {
	private long queryStartTime;
	private DateTime queryDate;
	private String querySsn;

	public QueryDataEvent(){
		this(null, null);
	}
	
	public QueryDataEvent(DateTime queryDate){
		this(queryDate, null);
	}
	
	public QueryDataEvent( String querySsn){
		this(null, querySsn);
	}
	
	public QueryDataEvent(DateTime queryDate, String querySsn) {

		this.queryStartTime = System.currentTimeMillis();
		this.queryDate = queryDate;
		this.querySsn = querySsn;
	}

	public long getQueryStartTime() {
		return queryStartTime;
	}

	public DateTime getQueryDate() {
		return queryDate;
	}

	public String getQuerySsn() {
		return querySsn;
	}

	@Override
	public String toString() {
		return "QueryDataEvent [queryStartTime=" + queryStartTime + ", queryDate=" + queryDate + ", querySsn=" + querySsn + "]";
	}

}
