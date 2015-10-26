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

import java.util.ArrayList;
import java.util.List;

import net.decix.jatlasx.data.TraceroutePath;
import net.decix.jatlasx.ripe.atlas.api.handler.TracerouteHandler;
import net.decix.jatlasx.ripe.atlas.http.HttpSubmitter;

public class MeasurementGathering {

	public static List<TraceroutePath> getMeasurementResultsbyID(long id) {
		if (id == -1) {
			return new ArrayList<TraceroutePath>();
		}
		HttpSubmitter submit = new HttpSubmitter(ApiKeys.measurement_url, "");
		String response = submit.get(id + "/result/?&format=json");
		if (response == null) {
			return new ArrayList<TraceroutePath>();
		}
		TracerouteHandler handler = new TracerouteHandler();
		return handler.handleResponse(response);

	}

	public static String getRawMeasurementResultsbyID(long id) {
		HttpSubmitter submit = new HttpSubmitter(ApiKeys.measurement_url, "");
		return submit.get(id + "/result/?&format=json");
	}
}
