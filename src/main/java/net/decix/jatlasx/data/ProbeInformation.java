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

public class ProbeInformation extends Writeable {

	private Long probe_id;
	private Long asn_number;
	private IpAddress ip_address;

	public ProbeInformation(Long ID, Long asn, IpAddress address) {
		this.probe_id = ID;
		this.asn_number = asn;
		this.ip_address = address;
	}

	@Override
	public List<String> getLine() {
		List<String> line = new ArrayList<String>();
		line.add(Long.toString(this.probe_id));
		line.add(Long.toString(this.asn_number));
		line.add(ip_address.toString());
		return line;
	}
}
