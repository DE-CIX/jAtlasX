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

import java.net.UnknownHostException;

public class IpRange {

	private IpAddress start;
	private IpAddress end;

	public IpRange(IpAddress start, IpAddress end) {
		this.start = start;
		this.end = end;
	}

	public IpRange(String start, String end) throws UnknownHostException {
		this.start = new IpAddress(start);
		this.end = new IpAddress(end);
	}

	public boolean isInRange(IpAddress address) {
		return (start.getLong() <= address.getLong() && end.getLong() >= address.getLong());
	}

}
