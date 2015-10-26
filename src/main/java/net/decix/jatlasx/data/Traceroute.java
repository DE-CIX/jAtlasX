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

import java.util.ArrayList;
import java.util.List;

public class Traceroute extends Writeable {

	private long asn_src;
	private long probe_src;
	private IpAddress ip_src;
	private long asn_dst;
	private long probe_dst;
	private IpAddress ip_dst;
	private long connection_id;
	private int connection_num;
	private int direction;
	private int asnConnection_num;
	private String description;

	public Traceroute(long asnSrc, long probeSrc, IpAddress source, long asnDst, long probeDst, IpAddress destination,
			long connectionID, int connectionNum, int direction, int asnConnectionNum, String description) {
		this.asn_src = asnSrc;
		this.probe_src = probeSrc;
		this.ip_src = source;
		this.asn_dst = asnDst;
		this.probe_dst = probeDst;
		this.ip_dst = destination;
		this.connection_id = connectionID;
		this.connection_num = connectionNum;
		this.direction = direction;
		this.asnConnection_num = asnConnectionNum;
		this.description = description;
	}

	@Override
	public List<String> getLine() {
		List<String> line = new ArrayList<String>();
		line.add(String.valueOf(this.asn_src));
		line.add(String.valueOf(this.probe_src));
		line.add(String.valueOf(this.ip_src.toString()));
		line.add(String.valueOf(this.asn_dst));
		line.add(String.valueOf(this.probe_dst));
		line.add(String.valueOf(this.ip_dst.toString()));
		line.add(String.valueOf(this.connection_id));
		line.add(String.valueOf(this.connection_num));
		line.add(String.valueOf(this.direction));
		line.add(String.valueOf(this.asnConnection_num));
		line.add(this.description);
		return line;
	}
}
