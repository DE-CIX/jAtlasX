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

public class MeasurementID extends Writeable {

	private Long measurement_id;
	private String asn_src;
	private String asn_dst;
	private String connection_id;
	private String connection_num;
	private String direction;
	private String asnConnection_num;

	public MeasurementID(long ID, String asnSrc, String asnDst, String connectionId, String connectionNum,
			String direction, String asnConnectionNum) {
		this.measurement_id = ID;
		this.asn_src = asnSrc;
		this.asn_dst = asnDst;
		this.connection_id = connectionId;
		this.connection_num = connectionNum;
		this.direction = direction;
		this.asnConnection_num = asnConnectionNum;
	}

	@Override
	public List<String> getLine() {
		List<String> line = new ArrayList<String>();
		line.add(Long.toString(this.measurement_id));
		line.add(this.asn_src);
		line.add(this.asn_dst);
		line.add(this.connection_id);
		line.add(this.connection_num);
		line.add(this.direction);
		line.add(this.asnConnection_num);
		return line;
	}
}
