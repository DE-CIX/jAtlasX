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
import net.decix.jatlasx.data.TraceroutePath;

public class TracerouteHandler implements ResponseHandler<TraceroutePath> {

	public List<TraceroutePath> handleResponse(String jsonString) {

		List<TraceroutePath> listOfPaths = new LinkedList<TraceroutePath>();

		JSONArray tracesListArray = new JSONArray(jsonString);

		for (int i = 0; i < tracesListArray.length(); i++) {
			JSONObject results = (JSONObject) tracesListArray.get(i);
			IpAddress source = null;
			IpAddress destination = null;
			try {
				source = new IpAddress((String) results.get("from"));
				destination = new IpAddress((String) results.get("dst_name"));
			} catch (UnknownHostException e) {
				String errorMsg = "Could not create IP-addresses";
				System.err.println(e.getClass().getName() + ":" + errorMsg + " (" + this.getClass().getName() + ")");
			}
			TracesHandler handler = new TracesHandler();
			String resultString = results.get("result").toString();
			List<IpAddress> trace = handler.handleResponse(resultString);

			TraceroutePath path = new TraceroutePath(source, destination, trace);
			listOfPaths.add(path);
		}
		return listOfPaths;
	}
}
