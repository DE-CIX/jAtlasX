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
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import net.decix.jatlasx.data.IpAddress;

public class TraceHandler implements ResponseHandler<IpAddress> {

	public List<IpAddress> handleResponse(String jsonString) {

		List<IpAddress> ipAddresses = new LinkedList<IpAddress>();
		JSONArray jsonArray = new JSONArray(jsonString);

		for (int i = 0; i < 1; i++) {
			JSONObject hop = (JSONObject) jsonArray.get(i);
			String ipAddress = "null";
			if (hop.has("from")) {
				ipAddress = String.valueOf(hop.get("from"));
			}

			try {
				ipAddresses.add(new IpAddress(ipAddress));
			} catch (UnknownHostException e) {
				String errorMsg = "Could not create IP-address:" + ipAddress;
				System.err.println(e.getClass().getName() + ":" + errorMsg + "(" + this.getClass().getName() + ")");
			}
		}
		return ipAddresses;
	}
}
