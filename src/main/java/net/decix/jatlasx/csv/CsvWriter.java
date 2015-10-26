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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import net.decix.jatlasx.data.Writeable;

public class CsvWriter {

	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final char DELIMETER = ',';
	private final CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR)
			.withDelimiter(DELIMETER);

	private String filename;

	private FileWriter fileWriter;
	private CSVPrinter printer;

	public CsvWriter(String filename, Object[] fileHeader) throws IOException {
		this.filename = filename;
		boolean fileExists = new File(filename).exists();
		fileWriter = new FileWriter(filename, true);
		printer = new CSVPrinter(fileWriter, csvFileFormat);

		// only write header if file does not exist yet
		if (!fileExists) {
			printer.printRecord(fileHeader);
		}

	}

	public void write(Writeable writeableObject) throws IOException {
		writeToCSV(writeableObject.getLine());
	}

	public void writeToCSV(List<String> line) throws IOException {
		printer.printRecord(line);
	}

	public String getFilename() {
		return filename;
	}

	public void closeFile() throws IOException {
		fileWriter.flush();
		fileWriter.close();
		printer.close();
	}

}
