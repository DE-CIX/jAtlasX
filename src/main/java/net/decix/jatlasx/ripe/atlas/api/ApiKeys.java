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
package net.decix.jatlasx.ripe.atlas.api;

public interface ApiKeys {

	String probe_url = "https://atlas.ripe.net/api/v1/probe/";
	String measurement_url = "https://atlas.ripe.net/api/v1/measurement/";

	String def = "definitions";

	String target = "target";
	String ip_version = "af";
	String timeout = "timeout";
	String dcp = "description";
	String protocol = "protocol";
	String resolve = "resolve_on_probe";
	String packets = "packets";
	String paris = "paris";
	String size = "size";
	String firstHop = "firsthop";
	String dstOption = "destination_option_size";
	String frag = "dontfrag";
	String type = "type";
	String list_of_probes = "probes";
	String oneoff = "is_oneoff";

	String probes = "probes";
	String tags = "tags";
	String value = "value";
	String requested = "requested";

	String ipv4 = "address_v4";
	String id = "id";
	String asn = "asn_v4";
	String isPublic = "is_public";
	String isConnected = "Connected";

	String status = "status_name";

}
