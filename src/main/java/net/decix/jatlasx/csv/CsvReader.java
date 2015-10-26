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
package net.decix.jatlasx.csv;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvReader {

	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final char DELIMETER = ',';
	private final CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR).withHeader()
			.withDelimiter(DELIMETER);
	private String fileName;

	public CsvReader(String fileName) {
		this.fileName = fileName;
	}

	public List<String> getColumn(String rowIdentifier) {

		CSVParser parser = null;
		List<String> column = new ArrayList<String>();

		try {
			parser = new CSVParser(new FileReader(fileName), csvFileFormat);

			if (parser != null) {
				for (CSVRecord record : parser) {
					column.add(record.get(rowIdentifier).toString());
				}
			}

			parser.close();
		} catch (IOException e) {
			String errorMsg = "Could not read File:" + fileName;
			System.err.println(e.getClass().getName() + ":" + errorMsg + " (" + this.getClass().getName() + ")");
		}
		return column;
	}

}
