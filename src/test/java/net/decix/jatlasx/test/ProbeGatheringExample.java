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

import java.util.List;

import net.decix.jatlasx.ripe.atlas.api.ProbeGathering;
import net.decix.jatlasx.ripe.atlas.json.Probe;

public class ProbeGatheringExample {

	public static void main(String[] args){
		List<Probe> probes = ProbeGathering.gatherProbesByASN(3320);
		
		// Print all IP addresses for the gathered probes
		for (Probe probe: probes){
			System.out.println(probe.getIpAddress());
		}
	}
	
}
