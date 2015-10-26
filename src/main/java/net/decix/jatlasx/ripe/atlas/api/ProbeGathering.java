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
import java.util.List;

import net.decix.jatlasx.csv.CsvWriter;
import net.decix.jatlasx.csv.Header;
import net.decix.jatlasx.data.IpAddress;
import net.decix.jatlasx.data.ProbeInformation;
import net.decix.jatlasx.ripe.atlas.api.handler.ProbeHandler;
import net.decix.jatlasx.ripe.atlas.api.handler.ProbeListHandler;
import net.decix.jatlasx.ripe.atlas.http.HttpSubmitter;
import net.decix.jatlasx.ripe.atlas.json.Probe;

public class ProbeGathering {

	public static void gatherProbesByASNtoCsv(long asn, String fileName, int numEntries) {

		HttpSubmitter submit = new HttpSubmitter(ApiKeys.probe_url, "");
		String response = submit.get("?asn=" + asn);

		ProbeListHandler handler = new ProbeListHandler(asn);
		List<Probe> probes = handler.handleResponse(response);
		try {

			CsvWriter probeWriter = new CsvWriter(fileName, Header.probe_header);
			int index = 0;
			for (Probe probe : probes) {
				if (index < numEntries) {
					probeWriter.write(
							new ProbeInformation(Long.valueOf(probe.getValue()), probe.getAsn(), probe.getIpAddress()));
					index++;
				}
			}
			probeWriter.closeFile();
		} catch (IOException e) {
			String errorMsg = "Could not write to:" + fileName;
			System.err.println(
					e.getClass().getName() + ":" + errorMsg + " (" + ProbeGathering.class.getClass().getName() + ")");

		}
	}

	public static List<Probe> gatherProbesByASN(long asn) {

		HttpSubmitter submit = new HttpSubmitter(ApiKeys.probe_url, "");
		String response = submit.get("?asn=" + asn);

		ProbeListHandler handler = new ProbeListHandler(asn);
		List<Probe> probes = handler.handleResponse(response);

		return probes;
	}

	public static IpAddress gatherProbeIPbyID(long id) {
		HttpSubmitter submit = new HttpSubmitter(ApiKeys.probe_url, "");
		String response = submit.get("?id=" + id);

		ProbeHandler handler = new ProbeHandler();
		List<IpAddress> address = handler.handleResponse(response);

		return address.get(0);
	}

}
