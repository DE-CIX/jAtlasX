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

public class ProbeHandler implements ResponseHandler<IpAddress> {

	public List<IpAddress> handleResponse(String jsonString) {

		JSONObject jsonObj = new JSONObject(jsonString);
		JSONArray probeDescArray = (JSONArray) jsonObj.get("objects");
		JSONObject probeDescObj = (JSONObject) probeDescArray.get(0);

		String ipAddress = (String) probeDescObj.get(ApiKeys.ipv4);

		List<IpAddress> list = new ArrayList<IpAddress>();
		IpAddress address = null;
		try {
			address = new IpAddress(ipAddress);
			list.add(address);
		} catch (UnknownHostException e) {
			String errorMsg = "Could not create IP-addresses";
			System.err.println(e.getClass().getName() + ":" + errorMsg + " (" + this.getClass().getName() + ")");
		}
		return list;
	}
}
