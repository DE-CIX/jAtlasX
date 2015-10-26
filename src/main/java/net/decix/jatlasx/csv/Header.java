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
package net.decix.jatlasx.csv;

public interface Header {

	String[] measurement_id_header = { "measurement_id", "asn_src", "asn_dst", "connection_id", "connection_num",
			"direction", "asnConnection_num" };
	String[] probe_header = { "probe_id", "asn_number", "ip_address" };
	String[] traceroute_header = { "asn_src", "probe_src", "ip_src", "asn_dst", "probe_dst", "ip_dst", "connection_id",
			"connection_num", "direction", "asnConnection_num", "description" };

}
