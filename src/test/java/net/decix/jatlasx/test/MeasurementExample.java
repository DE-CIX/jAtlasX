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

import java.net.UnknownHostException;
import java.util.List;

import net.decix.jatlasx.data.IpRange;
import net.decix.jatlasx.data.TraceroutePath;
import net.decix.jatlasx.ripe.atlas.api.MeasurementGathering;
import net.decix.jatlasx.ripe.atlas.api.measurement.ApiKeyException;
import net.decix.jatlasx.ripe.atlas.api.measurement.Measurement;
import net.decix.jatlasx.ripe.atlas.api.measurement.TracerouteMeasurement;

public class MeasurementExample {

	// please set an valid apiKey
	private static String apiKey = null;

	public static void main(String[] args) throws UnknownHostException, ApiKeyException {

		Measurement simpleMeasurement = new TracerouteMeasurement(apiKey);
		Long probeID = 21931L;
		Long measurementID = simpleMeasurement.createMeasurement(probeID, "8.8.8.8", "jAtlasX_test_measurement");
		System.out.println("MeasurementID: " + measurementID);

		// Uncomment to view sampleMeasurement
		// Long measurementID = 2863406L;

		List<TraceroutePath> pathes = MeasurementGathering.getMeasurementResultsbyID(measurementID);

		IpRange ipRangeOfDecix = new IpRange("DE-CIX", "80.81.192.0", "80.81.195.255");
		if (pathes.isEmpty()) {
			System.err.println("Measurement results are not yet available. Try again some minutes later");
		}
		for (TraceroutePath path : pathes) {
			System.out.println(path.toString());
			List<Integer> checkForDecix = path.checkIpRange(ipRangeOfDecix);
			System.out.println(ipRangeOfDecix.getName() + " was found on this trace route: " + !checkForDecix.isEmpty());
		}
	}
}
