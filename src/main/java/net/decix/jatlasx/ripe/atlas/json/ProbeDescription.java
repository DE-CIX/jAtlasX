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
package net.decix.jatlasx.ripe.atlas.json;

import java.util.LinkedList;

public class ProbeDescription {

	private final String TARGET;
	private final int IP_VERSION;
	private final int TIMEOUT;
	private final String DESCRIPTION;
	private final String PROTOCOL;
	private final boolean RESOLVE_ON_PROBE;
	private final int PACKETS;
	private final int SIZE;
	private final int FIRSTHOP;
	private final int DESTINATION_OPTION_SIZE;
	private final int PARIS;
	private final int MAX_HOPS;
	private final int HOP_BY_HOP_OPTION_SIZE;
	private final boolean DONT_FRAG;
	private final String TYPE;
	private final LinkedList<Probe> LIST_OF_PROBES;
	private final boolean IS_ONOFF;

	public ProbeDescription(final String newTarget, final int newIp_version, final int newTimeout,
			final String newDescription, final String newProtocol, final boolean newResolve_on_probe,
			final int newPackets, final int newSize, final int newFirsthop, final int newDestination_option_size,
			final int newParis, final int newMaxhops, final int newHop_by_hop_option_size, final boolean newDontfrag,
			final String newType, final boolean is_oneoff) {
		this.TARGET = newTarget;
		this.IP_VERSION = newIp_version;
		this.TIMEOUT = newTimeout;
		this.DESCRIPTION = newDescription;
		this.PROTOCOL = newProtocol;
		this.RESOLVE_ON_PROBE = newResolve_on_probe;
		this.PACKETS = newPackets;
		this.SIZE = newSize;
		this.FIRSTHOP = newFirsthop;
		this.DESTINATION_OPTION_SIZE = newDestination_option_size;
		this.PARIS = newParis;
		this.MAX_HOPS = newMaxhops;
		this.HOP_BY_HOP_OPTION_SIZE = newHop_by_hop_option_size;
		this.DONT_FRAG = newDontfrag;
		this.TYPE = newType;
		this.LIST_OF_PROBES = new LinkedList<Probe>();
		this.IS_ONOFF = is_oneoff;
	}

	public void addProbe(Probe probe) {
		LIST_OF_PROBES.add(probe);
	}

	public String getTarget() {
		return TARGET;
	}

	public int getIp_version() {
		return IP_VERSION;
	}

	public int getTimeout() {
		return TIMEOUT;
	}

	public String getDescription() {
		return DESCRIPTION;
	}

	public String getProtocol() {
		return PROTOCOL;
	}

	public boolean isResolve_on_probe() {
		return RESOLVE_ON_PROBE;
	}

	public int getPackets() {
		return PACKETS;
	}

	public int getSize() {
		return SIZE;
	}

	public int getFirsthop() {
		return FIRSTHOP;
	}

	public int getDestination_option_size() {
		return DESTINATION_OPTION_SIZE;
	}

	public int getParis() {
		return PARIS;
	}

	public int getMaxhops() {
		return MAX_HOPS;
	}

	public int getHop_by_hop_option_size() {
		return HOP_BY_HOP_OPTION_SIZE;
	}

	public boolean isDontfrag() {
		return DONT_FRAG;
	}

	public String getType() {
		return TYPE;
	}

	public LinkedList<Probe> getList_of_probes() {
		return LIST_OF_PROBES;
	}

	public boolean isIs_oneoff() {
		return IS_ONOFF;
	}

}
