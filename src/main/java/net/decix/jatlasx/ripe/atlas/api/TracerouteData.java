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
package net.decix.jatlasx.ripe.atlas.api;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.decix.jatlasx.csv.CsvReader;
import net.decix.jatlasx.csv.CsvWriter;
import net.decix.jatlasx.csv.Header;
import net.decix.jatlasx.data.ProbeList;
import net.decix.jatlasx.data.Traceroute;
import net.decix.jatlasx.data.UniqueID;
import net.decix.jatlasx.ripe.atlas.json.Probe;

public class TracerouteData {

	public static void generateTracerouteData(String pathToAsnList, String output, String csv_identifier) {
		CsvReader asnReader = new CsvReader(pathToAsnList);
		List<String> asnSrc = asnReader.getColumn(csv_identifier);

		Set<String> destinations = new HashSet<String>();

		destinations.addAll(asnSrc);

		Iterator<String> it = destinations.iterator();

		while (it.hasNext()) {
			String next = it.next();
			System.out.println(next);
			ProbeGathering.gatherProbesByASNtoCsv(Integer.valueOf(next), output, 10);
		}
	}

	public static void processASNList(String pathToAsnList, String pathToCustomerList, String sourceHeader,
			String destinationHeader, String customerHeader, String outputName) {

		// load ASN source & destination information from file
		CsvReader asnReader = new CsvReader(pathToAsnList);
		CsvReader customerReader = new CsvReader(pathToCustomerList);
		List<String> asnSrc = asnReader.getColumn(sourceHeader);
		List<String> asnDst = asnReader.getColumn(destinationHeader);
		List<String> asnCust = customerReader.getColumn(customerHeader);

		if (asnSrc.size() != asnDst.size()) {
			String errorMsg = "Number of Source ASNs does not match number of destination ASNs";
			System.err.println(Exception.class.getClass().getName() + ":" + errorMsg + " ("
					+ TracerouteData.class.getClass().getName() + ")");
			return;
		}

		// match source and destination list with the customer ASN listed in the
		// customer list
		List<String> sources = new LinkedList<String>();
		List<String> destinations = new LinkedList<String>();
		try {

			CsvWriter traceWriter = new CsvWriter(outputName, Header.traceroute_header);

			Set<Long> knownConnection = new HashSet<Long>();
			int id = 2;
			int connection_num = 1;
			int asnConnection_num = 1;
			for (int i = 0; i < asnSrc.size(); i++) {
				String source = asnSrc.get(i);
				String destination = asnDst.get(i);

				// check if connection has at least one asn which is present at
				// both
				// IXPs
				if ((asnCust.contains(source) && asnCust.contains(destination)) && !knownConnection
						.contains(UniqueID.getNewID(Long.valueOf(source), Long.valueOf(destination)))) {

					sources.add(source);
					destinations.add(destination);
					long asnConnect = UniqueID.getNewID(Long.valueOf(source), Long.valueOf(destination));
					knownConnection.add(asnConnect);

					// get available probes for both destination and source and
					// evaluate which possible connections should be tested
					ProbeList probeList = ProbeList.generateProbeList(
							ProbeGathering.gatherProbesByASN(Long.valueOf(source)),
							ProbeGathering.gatherProbesByASN(Long.valueOf(destination)));

					for (int y = 0; y < probeList.size(); y++) {

						Probe sourceProbe = probeList.getSources().get(y);
						Probe destinationProbe = probeList.getDestinations().get(y);
						traceWriter.write(new Traceroute(sourceProbe.getAsn(), Long.valueOf(sourceProbe.getValue()),
								sourceProbe.getIpAddress(), destinationProbe.getAsn(),
								Long.valueOf(destinationProbe.getValue()), destinationProbe.getIpAddress(), asnConnect,
								connection_num, 0, asnConnection_num, String.valueOf(id)));
						id++;
						traceWriter.write(new Traceroute(destinationProbe.getAsn(),
								Long.valueOf(destinationProbe.getValue()), destinationProbe.getIpAddress(),
								sourceProbe.getAsn(), Long.valueOf(sourceProbe.getValue()), sourceProbe.getIpAddress(),
								asnConnect, connection_num, 1, asnConnection_num, String.valueOf(id)));

						connection_num++;
						id++;
					}
					if (probeList.size() > 0) {
						asnConnection_num++;
					}
				}

			}

			traceWriter.closeFile();
		} catch (IOException e) {
			System.err.println("Could not write to " + outputName);
		}

	}

}
