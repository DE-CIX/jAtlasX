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

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpAddress {

	private InetAddress address;

	public IpAddress(String host) throws UnknownHostException {
		if (host.equals("null")) {
			this.address = InetAddress.getByName("localhost");
		} else {
			this.address = InetAddress.getByName(host);
		}
	}

	public long getLong() {
		byte[] octets = address.getAddress();
		long result = 0;
		for (byte octet : octets) {
			result <<= 8;
			result |= octet & 0xff;
		}
		return result;
	}

	@Override
	public String toString() {
		return address.getHostAddress();
	}

	public boolean compareAddress(IpAddress address) {
		return this.address.equals(address.address);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof IpAddress) {
			IpAddress address = (IpAddress) other;
			return address.address.equals(this.address);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return address.hashCode();
	}
}
