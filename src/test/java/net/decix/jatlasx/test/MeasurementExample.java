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

		Long measurementID = null;
		Measurement simpleMeasurement = new TracerouteMeasurement(apiKey);
		measurementID = simpleMeasurement.createMeasurement(21931L, "8.8.8.8", "jAtlasX_test_measurement");
		System.out.println("MeasurementID: " + measurementID);

		// Uncomment to view sampleMeasurement
		// measurementID = 2852412L;

		List<TraceroutePath> pathes = MeasurementGathering.getMeasurementResultsbyID(measurementID);

		for (TraceroutePath path : pathes) {
			System.out.println(path.toString());
			List<Integer> resultdecix = path.checkIpRange(new IpRange("80.81.192.0", "80.81.195.255"));
			if (resultdecix.isEmpty()) {
				System.out.println("DE-CIX not on the path");
			} else {
				System.out.println(resultdecix + " DE-CIX on the path");
			}
			List<Integer> resultamsix = path.checkIpRange(new IpRange("80.249.208.0", "80.249.215.255"));
			if (resultamsix.isEmpty()) {
				System.out.println("AMS-IX not on the path");
			} else {
				System.out.println(resultdecix + " AMS-IX on the path");
			}
		}
	}
}
