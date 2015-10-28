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
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;
import net.decix.jatlasx.csv.CsvReader;
import net.decix.jatlasx.data.IpAddress;
import net.decix.jatlasx.ripe.atlas.api.ProbeGathering;
import net.decix.jatlasx.ripe.atlas.json.Probe;

public class ProbeReceiverTest extends TestCase {

	@Test
	public void testProbeGathering() {

		// Check if File is deleted
		String fileName = "probes.csv";
		boolean fileExists = new File(fileName).exists();
		File file = new File(fileName);

		if (fileExists) {
			file.delete();
		}

		// Check this ASN for Probes
		List<Long> asnList = new ArrayList<Long>();
		asnList.add((long) 12989);
		asnList.add((long) 1273);
		asnList.add((long) 25220);

		// ProbeList for Method gatherProbesByASN
		List<Probe> probeIDListfromgatherProbesByASN = new ArrayList<Probe>();

		// Write the Probes to csv
		for (Long asn : asnList) {
			ProbeGathering.gatherProbesByASNtoCsv(asn, fileName, 20);
			probeIDListfromgatherProbesByASN.addAll(ProbeGathering.gatherProbesByASN(asn));
		}

		// Read probeID and probeIpAdresses from csv
		CsvReader probeReader = new CsvReader(fileName);
		List<String> probeIDList = probeReader.getColumn("probe_id");
		List<String> probeIpAddressList = probeReader.getColumn("ip_address");

		// Check if for each ID Entry exist an IpAddress
		if (probeIDList.size() != probeIpAddressList.size()) {
			assertFalse(true);
		}

		// Check if IP in csv is the same as Method gatherProbeIPbyID
		for (int i = 0; i < probeIDList.size(); i++) {
			Long probeID = Long.valueOf(probeIDList.get(i));
			IpAddress probeIp = ProbeGathering.gatherProbeIPbyID(probeID);
			String stringProbeIp = probeIp.toString();

			assertEquals(probeIpAddressList.get(i), stringProbeIp);
		}

		// Check if ID in csv is the same as Method gatherProbesByASN
		for (int i = 0; i < probeIDList.size(); i++) {
			assertEquals(probeIDList.get(i), probeIDListfromgatherProbesByASN.get(i).getValue());
		}

		if (fileExists) {
			file.delete();
		}
	}

}
