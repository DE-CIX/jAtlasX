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

import org.json.JSONObject;

public class Probe {

	private JSONObject tags;
	private String type;
	private String id;
	private int requested;
	private long asn;
	private IpAddress ipAddress;

	public Probe(String type, Long value, int requested) {
		this.type = type;
		this.id = String.valueOf(value);
		this.requested = requested;
		tags = new JSONObject();
	}

	public void setAsn(long asn) {
		this.asn = asn;
	}

	public void setIpAddress(IpAddress address) {
		this.ipAddress = address;
	}

	public IpAddress getIpAddress() {
		return ipAddress;
	}

	public long getAsn() {
		return this.asn;
	}

	public JSONObject getTags() {
		return tags;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public int getRequested() {
		return requested;
	}

	@Override
	public String toString() {
		return "[Probe ID: " + id + "; Type: " + type + "; Requested: " + requested + "]";
	}

}
