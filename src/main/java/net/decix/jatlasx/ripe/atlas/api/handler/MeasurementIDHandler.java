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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MeasurementIDHandler implements ResponseHandler<Long> {

	public List<Long> handleResponse(String jsonString) {

		JSONObject jsonObj = new JSONObject(jsonString);
		JSONArray jsonArray = (JSONArray) jsonObj.get("measurements");

		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < jsonArray.length(); i++) {
			list.add((Long) jsonArray.getLong(i));
		}
		return list;
	}
}
