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

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import net.decix.jatlasx.data.IpAddress;

public class TracesHandler implements ResponseHandler<IpAddress> {

	public List<IpAddress> handleResponse(String jsonString) {

		List<IpAddress> traceIPs = new LinkedList<IpAddress>();
		JSONArray jsonArray = new JSONArray(jsonString);

		for (int y = 0; y < jsonArray.length(); y++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(y);
			JSONArray resultArray = new JSONArray(jsonObject.get("result").toString());

			if (resultArray != null) {
				TraceHandler handler = new TraceHandler();
				traceIPs.addAll(handler.handleResponse(resultArray.toString()));
			}
		}
		return traceIPs;
	}

}
