/*
 * This file is part of jAtlasX
 *
 * Copyright (c) 2015 DE-CIX Management GmbH <http://www.de-cix.net> - All rights
 * reserved.
 * 
 * @author: Sascha Bleidner <sascha.bleidner@de-cix.net>
 *
 * This software is licensed under the Apache License, version 2.0. A copy of 
 * the license agreement is included in this distribution.
 */
package net.decix.jatlasx.test;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;
import net.decix.jatlasx.csv.CsvReader;
import net.decix.jatlasx.csv.CsvWriter;
import net.decix.jatlasx.csv.Header;
import net.decix.jatlasx.data.IpAddress;
import net.decix.jatlasx.data.MeasurementID;
import net.decix.jatlasx.data.ProbeInformation;
import net.decix.jatlasx.data.Traceroute;

public class CsvTest extends TestCase {

	@Test
	public void testWriterAndReader() throws UnknownHostException {
		boolean receivedData = false;
		String fileName1 = "test1.csv";
		String fileName2 = "test2.csv";
		String fileName3 = "test3.csv";
		String fileName4 = "test4.csv";

		// create writer
		CsvWriter writer1;
		try {
			writer1 = new CsvWriter(fileName1, Header.measurement_id_header);

			CsvWriter writer2 = new CsvWriter(fileName2, Header.probe_header);
			CsvWriter writer3 = new CsvWriter(fileName3, Header.traceroute_header);
			CsvWriter writer4 = new CsvWriter(fileName4, new Object[1]);

			// right filename
			if (writer1.getFilename() != fileName1) {
				fail("wrong Filename " + fileName1);
			}
			if (writer2.getFilename() != fileName2) {
				fail("wrong Filename " + fileName2);
			}
			if (writer3.getFilename() != fileName3) {
				fail("wrong Filename " + fileName3);
			}
			if (writer4.getFilename() != fileName4) {
				fail("wrong Filename " + fileName4);
			}

			// fill datastruct
			List<Long> data1 = new ArrayList<Long>();
			for (int i = 0; i < 5000; i++) {
				MeasurementID measurement1 = new MeasurementID(Integer.toUnsignedLong(i), "0", "0", "0", "0", "0", "0");
				writer1.write(measurement1);
				data1.add(Integer.toUnsignedLong(i));
			}

			List<Long> data2 = new ArrayList<Long>();
			for (int i = 0; i < 5000; i++) {
				ProbeInformation measurement2 = new ProbeInformation(Integer.toUnsignedLong(i),
						Integer.toUnsignedLong(i), new IpAddress("192.168.1.1"));
				writer2.write(measurement2);
				data2.add(Integer.toUnsignedLong(i));
			}

			List<Long> data3 = new ArrayList<Long>();
			for (int i = 0; i < 5000; i++) {
				Traceroute measurement3 = new Traceroute(Integer.toUnsignedLong(i), Integer.toUnsignedLong(i),
						new IpAddress("192.168.1.1"), Integer.toUnsignedLong(i), Integer.toUnsignedLong(i),
						new IpAddress("192.168.1.1"), i, i, i, i, fileName4);
				writer3.write(measurement3);
				data3.add(Integer.toUnsignedLong(i));
			}

			List<Long> data4 = new ArrayList<Long>();
			for (int i = 0; i < 5000; i++) {
				List<String> measurement4 = new ArrayList<String>();
				measurement4.add(String.valueOf(i));
				writer4.writeToCSV(measurement4);
				data4.add(Integer.toUnsignedLong(i));
			}

			// close writer
			writer1.closeFile();
			writer2.closeFile();
			writer3.closeFile();
			writer4.closeFile();

			// check data
			receivedData = false;
			CsvReader reader1 = new CsvReader(fileName1);
			List<String> column1 = reader1.getColumn("measurement_id");

			for (int i = 0; i < column1.size(); i++) {
				receivedData = true;
				Long storedValue = data1.get(i);
				Long parsedValue = Long.parseLong(column1.get(i));
				assertEquals(storedValue, parsedValue);
			}
			assertTrue(receivedData);

			receivedData = false;
			CsvReader reader2 = new CsvReader(fileName2);
			List<String> column2 = reader2.getColumn("asn_number");

			for (int i = 0; i < column2.size(); i++) {
				receivedData = true;
				Long storedValue = data2.get(i);
				Long parsedValue = Long.parseLong(column2.get(i));
				assertEquals(storedValue, parsedValue);
			}
			assertTrue(receivedData);

			receivedData = false;
			CsvReader reader3 = new CsvReader(fileName3);
			List<String> column3 = reader3.getColumn("asn_dst");

			for (int i = 0; i < column3.size(); i++) {
				receivedData = true;
				Long storedValue = data3.get(i);
				Long parsedValue = Long.parseLong(column3.get(i));
				assertEquals(storedValue, parsedValue);
			}
			assertTrue(receivedData);

			receivedData = false;

			// clear measurement
			new File(fileName1).delete();
			new File(fileName2).delete();
			new File(fileName3).delete();
			new File(fileName4).delete();

		} catch (IOException e) {
			assertTrue(false);
			System.err.print("Could not open writer for tests");
		}

	}

	@Test
	public void testCsvWriterAndReader() {

		boolean receivedData = false;
		String fileName = "test.csv";

		try {

			// single column
			CsvWriter writer = new CsvWriter(fileName, Header.measurement_id_header);
			List<Long> ids = new ArrayList<Long>();

			for (int i = 0; i < 5000; i++) {
				writer.write(new MeasurementID(Integer.toUnsignedLong(i), "0", "0", "0", "0", "0", "0"));
				ids.add(Integer.toUnsignedLong(i));
			}
			writer.closeFile();

			CsvReader reader = new CsvReader(fileName);
			List<String> column = reader.getColumn("measurement_id");

			for (int i = 0; i < column.size(); i++) {
				receivedData = true;
				Long storedValue = ids.get(i);
				Long parsedValue = Long.parseLong(column.get(i));

				assertEquals(storedValue, parsedValue);
			}

			assertTrue(receivedData);
			receivedData = false;
			new File(fileName).delete();

			// multiple columns
			String[] header2 = { "colum1", "colum2" };

			CsvWriter writer2 = new CsvWriter(fileName, header2);
			ids = new ArrayList<Long>();
			List<Long> numbers = new ArrayList<Long>();

			for (int i = 0; i < 5000; i++) {
				List<String> line = new ArrayList<String>();
				line.add(Integer.toString(i));
				line.add(Integer.toString(i * 10));

				writer2.writeToCSV(line);
				ids.add(Integer.toUnsignedLong(i));
				numbers.add(Integer.toUnsignedLong(i * 10));
			}
			writer2.closeFile();

			CsvReader reader2 = new CsvReader(fileName);
			List<String> column1 = reader2.getColumn(header2[0]);
			List<String> column2 = reader2.getColumn(header2[1]);

			for (int i = 0; i < column1.size(); i++) {
				receivedData = true;
				Long stored_id = ids.get(i);
				Long stored_number = numbers.get(i);
				Long parsed_id = Long.parseLong(column1.get(i));
				Long parsed_number = Long.parseLong(column2.get(i));

				assertEquals(stored_id, parsed_id);
				assertEquals(stored_number, parsed_number);
			}

			assertTrue(receivedData);
			receivedData = false;
			new File(fileName).delete();
		} catch (IOException e) {
			assertTrue(false);
			System.err.print("Could not open writer for tests");
		}
	}

}
