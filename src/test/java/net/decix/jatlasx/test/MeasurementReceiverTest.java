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

import junit.framework.TestCase;
import org.junit.Test;

import net.decix.jatlasx.data.IpRange;
import net.decix.jatlasx.data.TraceroutePath;
import net.decix.jatlasx.ripe.atlas.api.MeasurementGathering;

public class MeasurementReceiverTest extends TestCase {

	boolean printResults = false;

	@Test
	public void testMeasurementGathering() {
		List<TraceroutePath> measurements = MeasurementGathering.getMeasurementResultsbyID(2079125);

		for (TraceroutePath path : measurements) {
			if (printResults) {
				System.out.println("TEST (MeasurementReceiverTest) - Path: " + "path");
			}
			try {
				assertTrue(path.checkIpRange(new IpRange("DE-CIX","80.81.192.0", "80.81.195.255")).isEmpty());
				assertTrue(path.checkIpRange(new IpRange("AMS-IX","80.249.208.0", "80.249.215.255")).isEmpty());
			} catch (UnknownHostException e) {
				String errorMsg = "Could not create IP-addresses";
				System.err.println(e.getClass().getName() + ":" + errorMsg + "(" + this.getClass().getName() + ")");
			}
		}
	}

}
