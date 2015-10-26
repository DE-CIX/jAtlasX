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

public class ProbeDescriptionBuilder {

	private String target;
	private int ip_version;
	private int timeout;
	private String description;
	private String protocol;
	private boolean resolve_on_probe;
	private int packets;
	private int size;
	private int firsthop;
	private int destination_option_size;
	private int paris;
	private int maxhops;
	private int hop_by_hop_option_size;
	private boolean dontfrag;
	private String type;
	private boolean is_oneoff;

	public ProbeDescriptionBuilder() {
	}

	public ProbeDescriptionBuilder setTaget(String target) {
		this.target = target;
		return this;
	}

	public ProbeDescriptionBuilder setIpVersion(int ip_version) {
		this.ip_version = ip_version;
		return this;
	}

	public ProbeDescriptionBuilder setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	public ProbeDescriptionBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	public ProbeDescriptionBuilder setProtocol(String protocol) {
		this.protocol = protocol;
		return this;
	}

	public ProbeDescriptionBuilder setResolveOnProbe(boolean resolve) {
		this.resolve_on_probe = resolve;
		return this;
	}

	public ProbeDescriptionBuilder setPackets(int packets) {
		this.packets = packets;
		return this;
	}

	public ProbeDescriptionBuilder setSize(int size) {
		this.size = size;
		return this;
	}

	public ProbeDescriptionBuilder setFirstHop(int firsthop) {
		this.firsthop = firsthop;
		return this;
	}

	public ProbeDescriptionBuilder setDestinationOption(int option) {
		this.destination_option_size = option;
		return this;
	}

	public ProbeDescriptionBuilder setParis(int paris) {
		this.paris = paris;
		return this;
	}

	public ProbeDescriptionBuilder setMaxHops(int maxHops) {
		this.maxhops = maxHops;
		return this;
	}

	public ProbeDescriptionBuilder setHobByHopOptionSize(int hobByHob) {
		this.hop_by_hop_option_size = hobByHob;
		return this;
	}

	public ProbeDescriptionBuilder setDontFrag(boolean dontFrag) {
		this.dontfrag = dontFrag;
		return this;
	}

	public ProbeDescriptionBuilder setType(String type) {
		this.type = type;
		return this;
	}

	public ProbeDescriptionBuilder setIsOneOff(boolean isOneOff) {
		this.is_oneoff = isOneOff;
		return this;
	}

	public ProbeDescription build() {
		return new ProbeDescription(target, ip_version, timeout, description, protocol, resolve_on_probe, packets, size,
				firsthop, destination_option_size, paris, maxhops, hop_by_hop_option_size, dontfrag, type, is_oneoff);
	}

}
