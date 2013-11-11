package jaxdemo.datagenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class RandomDataGenerator {
	private Random randomGenerator = new Random();
	private Set<String> ssnSet = new HashSet<String>();
	private List<String> ssnList = new ArrayList<String>();

	String getName(List<String> names) {

		int randomIndex = randomGenerator.nextInt(names.size());
		return names.get(randomIndex);

	}
	
	String chooseSsn(){
		if (ssnList.size() <1000){
			return generateSsn();
		}
		else{
			return ssnList.get(randomGenerator.nextInt(ssnList.size()));
		}
	}

	String generateSsn() {
		int pos1 = randomGenerator.nextInt(1000);
		int pos2 = randomGenerator.nextInt(100);
		int pos3 = randomGenerator.nextInt(10000);
		String ssn = "" + String.format("%03d", pos1) + "-" + String.format("%02d", pos2) + "-" + String.format("%04d", pos3);

		if (ssnSet.contains(ssn)) {
			return generateSsn();
		} else {
			ssnSet.add(ssn);
			ssnList.add(ssn);
			return ssn;
		}

	}

	DateTime getDob(DateTime startDob, DateTime endDob) {
		int days = Days.daysBetween(startDob, endDob).getDays();
		int randomDays = randomGenerator.nextInt(days + 1);

		return startDob.plusDays(randomDays);
	}

	public static void main(String args[]) throws IOException {
		List<String> names = getNames();
		DateTime startDob = DateTime.parse("1990-01-01");
		DateTime endDob = DateTime.parse("1990-12-31");
		RandomDataGenerator rdg = new RandomDataGenerator();
		
		writeFile("insertDataFile", rdg, names, startDob, endDob, false);
		writeFile("updateDataFile", rdg, names, startDob, endDob, true);
	}
	
	private static void writeFile(String filename, RandomDataGenerator rdg, List<String> names, DateTime startDob, DateTime endDob, boolean chooseSsn) throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(filename);
		
		for (int i = 0; i < 1000000; i++) {
			DataRow r = new DataRow(rdg, names, startDob, endDob, false);
			writer.println(r);
		}
		writer.close();
	}

	private static List<String> getNames() throws IOException {
		List<String> names = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(RandomDataGenerator.class.getResourceAsStream("sampleNames")));
		String line;
		while ((line = br.readLine()) != null) {
			if(line.trim().length() >0) 
				names.add(line.trim());
		}
		return names;

	}

}
