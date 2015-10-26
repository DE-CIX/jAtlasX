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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.decix.jatlasx.csv.CsvWriter;
import net.decix.jatlasx.data.IpAddress;
import net.decix.jatlasx.ripe.atlas.http.HttpSubmitter;

public abstract class Measurement {

	protected HttpSubmitter httpClient;
	protected CsvWriter idWriter;
	protected int submittedMeasurements;
	private static Map<IpAddress, Integer> countTargets = new HashMap<IpAddress, Integer>();
	private static int defaultSleep = 600000;
	private static String url = "https://atlas.ripe.net/api/v1/measurement/";

	private int startAtHop = 1;
	private int maxHops = 32;
	private int packetSize = 48;
	private int timeout = 4000;
	private boolean dontFrag = false;

	public Measurement(String apiKey) throws ApiKeyException {
		if (apiKey == null) {
			throw new ApiKeyException("NoApiKey");
		}
		this.httpClient = new HttpSubmitter(url, apiKey);
		this.submittedMeasurements = 0;
	}

	public abstract Long createMeasurement(Long probeId, String targetIp, String description);

	public void nextMeasurement(IpAddress destination) {
		submittedMeasurements++;
		int counter = 1;
		if (countTargets.containsKey(destination)) {
			counter = countTargets.get(destination);
			counter++;
		}

		countTargets.put(destination, counter);

		int sleepTime = 0;
		if (submittedMeasurements == 99) {
			sleepTime = defaultSleep;
			System.out.println("Waiting because of number of experiments");
		} else {
			if (!checkTargetMap(countTargets)) {
				sleepTime = defaultSleep;
				System.out.println("Waiting because of Target limits");
			}
		}

		if (sleepTime > 0) {
			submittedMeasurements = 0;
			countTargets.clear();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.err.println("InteruptedException in sleepTime Thread (Measurement)");
			}
		}

	}

	// method is used to prevent the RIPE Atlas to answer with an error, since
	// the program requested more than 10 times the same target.
	public static boolean checkTargetMap(Map<IpAddress, Integer> map) {
		Set<IpAddress> keys = map.keySet();
		for (IpAddress key : keys) {
			if (map.get(key) == 10) {
				map.remove(key);
				System.out.println("Wait because of target limit at: " + key);
				return false;
			}
		}
		return true;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		Measurement.url = url;
	}

	public int getStartAtHop() {
		return startAtHop;
	}

	public void setStartAtHop(int startAtHop) {
		this.startAtHop = startAtHop;
	}

	public int getMaxHops() {
		return maxHops;
	}

	public void setMaxHops(int maxHops) {
		this.maxHops = maxHops;
	}

	public int getPacketSize() {
		return packetSize;
	}

	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public boolean isDontFrag() {
		return dontFrag;
	}

	public void setDontFrag(boolean dontFrag) {
		this.dontFrag = dontFrag;
	}
}
