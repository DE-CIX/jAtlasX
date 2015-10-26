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
package net.decix.jatlasx.ripe.atlas.api.handler;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import net.decix.jatlasx.data.IpAddress;
import net.decix.jatlasx.ripe.atlas.api.ApiKeys;
import net.decix.jatlasx.ripe.atlas.json.Probe;

public class ProbeListHandler implements ResponseHandler<Probe> {

	private long asn;

	public ProbeListHandler(long asNumber) {
		asn = asNumber;
	}

	public List<Probe> handleResponse(String jsonString) {

		List<Probe> probes = new ArrayList<Probe>();

		JSONObject jsonObj = new JSONObject(jsonString);
		JSONArray probeListArray = (JSONArray) jsonObj.get("objects");

		for (int i = 0; i < probeListArray.length(); i++) {
			JSONObject probeObj = (JSONObject) probeListArray.get(i);

			String ipAddress = (String) probeObj.get(ApiKeys.ipv4);
			Long id = (Long) probeObj.getLong(ApiKeys.id);
			boolean isPublic = (Boolean) probeObj.get(ApiKeys.isPublic);
			Long asnNum = (Long) probeObj.getLong(ApiKeys.asn);
			String status = (String) probeObj.get(ApiKeys.status);
			// only select probes which provide an IPv4 address
			if (ipAddress != null && asn == asnNum && status.equals("Connected") && isPublic) {
				Probe newProbe = new Probe(ApiKeys.probes, id, 1);
				newProbe.setAsn(asnNum);
				try {
					newProbe.setIpAddress(new IpAddress(ipAddress));
				} catch (UnknownHostException e) {
					String errorMsg = "Could not create IP-address:" + ipAddress;
					System.err.println(e.getClass().getName() + ":" + errorMsg + "(" + this.getClass().getName() + ")");
				}
				probes.add(newProbe);
			}
		}
		return probes;
	}
}
