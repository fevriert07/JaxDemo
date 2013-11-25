package jax.java.jaxdemo.datagenerator;

import java.util.List;

import org.joda.time.DateTime;

public class DataRow {
	private String firstName;
	private String middleInitial;
	private String lastName;
	private DateTime dob;
	private String ssn;

	public DataRow(RandomDataGenerator rdg, List<String> names, DateTime startDob, DateTime endDob, boolean chooseSsn) {
		this.firstName = rdg.getName(names);
		this.middleInitial = rdg.getName(names).substring(0, 1);
		this.lastName = rdg.getName(names);
		this.ssn = chooseSsn ? rdg.chooseSsn() : rdg.generateSsn();
		this.dob = rdg.getDob(startDob, endDob);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public DateTime getDob() {
		return dob;
	}

	public String getSsn() {
		return ssn;
	}

	public String toString() {
		return firstName + "," + middleInitial + "," + lastName + "," + ssn + "," + dob.toLocalDate();
	}

}
