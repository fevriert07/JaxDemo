package jaxdemo.events;

import org.joda.time.DateTime;

import com.lmax.disruptor.EventFactory;

public final class PersonRecordEvent implements Event {

	private String firstName;
	private String lastName;
	private String middleInitial;
	private String socialSecurityNumber;
	private DateTime dateOfBirth;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public DateTime getDateOfBirth() {
		return dateOfBirth;
	}

	public void fillPersonRecordEvent(String firstName, String middleInitial, String lastName, String socialSecurityNumber, DateTime dateOfBirth) {

		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.socialSecurityNumber = socialSecurityNumber;
		this.dateOfBirth = dateOfBirth;
	}

	// Used by disruptor ringbuffer to issue event
	public final static EventFactory<PersonRecordEvent> EVENT_FACTORY = new EventFactory<PersonRecordEvent>() {
		public PersonRecordEvent newInstance() {
			return new PersonRecordEvent();
		}
	};

}
