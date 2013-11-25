package jax.java.jaxdemo.events;

import org.joda.time.DateTime;

public final class PersonRecordEvent {

	private String firstName;
	private String lastName;
	private String middleInitial;
	private String ssn;
	private DateTime dob;

	public PersonRecordEvent(String firstName, String middleInitial, String lastName, String ssn, DateTime dob) {
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.ssn = ssn;
		this.dob = dob;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public String getSsn() {
		return ssn;
	}

	public DateTime getDob() {
		return dob;
	}

	@Override
	public String toString() {
		return "PersonRecordEvent [firstName=" + firstName + ", lastName=" + lastName + ", middleInitial=" + middleInitial + ", ssn=" + ssn + ", dob="
				+ dob.toLocalDate() + "]";
	}

}
