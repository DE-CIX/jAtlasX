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
package net.decix.jatlasx.ripe.atlas.api.measurement;

import java.net.UnknownHostException;
import java.util.List;

import net.decix.jatlasx.data.IpAddress;
import net.decix.jatlasx.data.Probe;
import net.decix.jatlasx.ripe.atlas.api.handler.MeasurementIDHandler;
import net.decix.jatlasx.ripe.atlas.api.handler.ResponseHandler;
import net.decix.jatlasx.ripe.atlas.json.JsonProbeBuilder;
import net.decix.jatlasx.ripe.atlas.json.ProbeDescription;
import net.decix.jatlasx.ripe.atlas.json.ProbeDescriptionBuilder;

public class TracerouteMeasurement extends Measurement {

	public TracerouteMeasurement(String apiKey) throws ApiKeyException {
		super(apiKey);
	}

	// TODO: Multiple probes in later versions
	public Long createMeasurement(Long probeId, String targetIp, String description) {

		Long result = -1L;
		List<Long> ids = null;

		// build JSON formatted probe description
		ProbeDescription desc = new ProbeDescriptionBuilder().setTaget(targetIp).setIpVersion(4)
				.setTimeout(getTimeout()).setDescription(description).setProtocol("ICMP").setResolveOnProbe(false)
				.setPackets(2).setSize(getPacketSize()).setFirstHop(getStartAtHop()).setDestinationOption(0).setParis(0)
				.setMaxHops(getMaxHops()).setHobByHopOptionSize(0).setDontFrag(isDontFrag()).setType("traceroute")
				.setIsOneOff(true).build();

		Probe probe = new Probe("probes", probeId, 1);

		desc.addProbe(probe);

		JsonProbeBuilder jsonBuilder = new JsonProbeBuilder(desc);

		IpAddress destination = null;

		try {
			destination = new IpAddress(targetIp);
		} catch (UnknownHostException e) {
			String errorMsg = "Could not create IP-addresses";
			System.err.println(e.getClass().getName() + ":" + errorMsg + " (" + this.getClass().getName() + ")");
			e.printStackTrace();
		}

		// checks if next measurement can be submitted or if the program has to
		// wait because of the RIPE atlas restrictions.
		this.nextMeasurement(destination);

		String response = httpClient.submit(jsonBuilder.getJsonString());

		if (response != null) {
			ResponseHandler<Long> handler = new MeasurementIDHandler();
			ids = handler.handleResponse(response);
			if (ids != null) {
				result = ids.get(0);
			}
		}

		return result;
	}

}
