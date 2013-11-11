package jaxdemo.datagenerator;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

public class RandomDataGeneratorTest {

	@Test
	public void testGetName() {
		// Given
		List<String> names = Arrays.asList("John", "Henry", "Jane", "William");
		RandomDataGenerator sut = new RandomDataGenerator();

		// when
		String name = sut.getName(names);

		// then
		assertTrue(names.contains(name));
	}

	@Test
	public void testGenerateSsn() {
		// Given
		RandomDataGenerator sut = new RandomDataGenerator();

		// when
		String ssn1 = sut.generateSsn();
		String ssn2 = sut.generateSsn();
				

		// then
		assertTrue(ssn1.matches("\\d{3}-\\d{2}-\\d{4}"));
		assertTrue(ssn2.matches("\\d{3}-\\d{2}-\\d{4}"));
		assertNotEquals(ssn1, ssn2);
		
	}

	@Test
	public void testGetDob() throws ParseException {
		// Given
		DateTime startDob = DateTime.parse("1985-01-01");
		DateTime endDob = DateTime.parse("1995-01-01");
		RandomDataGenerator sut = new RandomDataGenerator();

		// When
		DateTime dob = sut.getDob(startDob, endDob);

		// Then
		assertTrue(dob.compareTo(startDob) >= 0);
		assertTrue(dob.compareTo(endDob) <= 0);

	}

}
