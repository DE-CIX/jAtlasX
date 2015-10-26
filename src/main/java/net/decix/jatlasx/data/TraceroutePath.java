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
import java.util.LinkedList;
import java.util.List;

public class TraceroutePath {

	private IpAddress source;
	private IpAddress destination;
	private List<IpAddress> path;
	private long id;

	public TraceroutePath(IpAddress source, IpAddress destination, List<IpAddress> path) {
		this.source = source;
		this.destination = destination;
		this.path = new LinkedList<IpAddress>();
		this.path.add(source);
		this.path.addAll(path);
	}

	public void setID(long id) {
		this.id = id;
	}

	public long getID() {
		return id;
	}

	public IpAddress getDst() {
		return destination;
	}

	public IpAddress getScr() {
		return source;
	}

	public List<Integer> checkIpRange(IpRange range) {
		int index = 0;
		List<Integer> result = new ArrayList<Integer>();
		// one of the hops is in the range;
		for (IpAddress address : path) {
			if (range.isInRange(address)) {
				result.add(index);
			}
			index++;
		}
		if (result.size() > 1) {
			// System.out.println("There seem to be two IXPs: " + this);
		}
		return result;
	}

	public long getKey() {
		return source.getLong() + destination.getLong();
	}

	public int getLength() {
		return path.size();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ID: ").append(id).append(" ");
		builder.append("[");
		for (int i = 0; i < path.size(); i++) {
			builder.append(path.get(i));

			if (i < path.size() - 1) {
				builder.append("; ");
			}
		}
		builder.append("]");
		return builder.toString();
	}

}
