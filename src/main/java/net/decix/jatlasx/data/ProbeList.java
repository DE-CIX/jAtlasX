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
package net.decix.jatlasx.data;

import java.util.LinkedList;
import java.util.List;

import net.decix.jatlasx.ripe.atlas.json.Probe;

public class ProbeList {

	private List<Probe> sources;
	private List<Probe> destinations;

	private ProbeList(List<Probe> sources, List<Probe> destinations) {
		this.sources = sources;
		this.destinations = destinations;
	}

	public static ProbeList generateProbeList(List<Probe> sources, List<Probe> destinations) {

		List<Probe> newSources = new LinkedList<Probe>();
		List<Probe> newDestinations = new LinkedList<Probe>();

		if (sources.size() != 0 && destinations.size() != 0) {

			for (int i = 0; i < Math.max(sources.size(), destinations.size()); i++) {
				newSources.add(sources.get(i % sources.size()));
				newDestinations.add(destinations.get(i % destinations.size()));
			}
		}
		return new ProbeList(newSources, newDestinations);
	}

	public List<Probe> getSources() {
		return sources;
	}

	public void setSources(List<Probe> sources) {
		this.sources = sources;
	}

	public List<Probe> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<Probe> destinations) {
		this.destinations = destinations;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < sources.size(); i++) {
			str.append(sources.get(i).getAsn()).append(" | ID: ").append(sources.get(i).getValue()).append("->")
					.append(destinations.get(i).getAsn()).append(" | ID: ").append(destinations.get(i).getValue())
					.append("\n");
		}

		return str.toString();
	}

	public int size() {
		return sources.size();
	}

}
