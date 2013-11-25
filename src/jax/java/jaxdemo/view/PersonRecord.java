package jax.java.jaxdemo.view;

import org.joda.time.DateTime;

public class PersonRecord {
	private String id;
	private String firstName;
	private String lastName;
	private String middleInitial;
	private String ssn;
	private DateTime dob;
	
	public PersonRecord(String id, String firstName,  String middleInitial, String lastName,String ssn, DateTime dob) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.ssn = ssn;
		this.dob = dob;
	}

	public String getId() {
		return id;
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
		return "PersonRecord [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", middleInitial=" + middleInitial + ", ssn=" + ssn
				+ ", dob=" + dob.toLocalDate() + "]";
	}
	
	
	

}
