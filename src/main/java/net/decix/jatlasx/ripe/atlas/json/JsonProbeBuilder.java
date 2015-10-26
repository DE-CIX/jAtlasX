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

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONObject;

import net.decix.jatlasx.ripe.atlas.api.ApiKeys;

public class JsonProbeBuilder extends JsonBuilder {

	@SuppressWarnings("unused")
	private ProbeDescription probe;

	public JsonProbeBuilder(ProbeDescription probe) {
		super();
		this.probe = probe;
		transformProbeDescription(probe);
	}

	private void transformProbeDescription(ProbeDescription probe) {

		LinkedList<Map<String, Object>> probeDescription = new LinkedList<Map<String, Object>>();
		LinkedList<Object> probes = new LinkedList<Object>();

		Map<String, Object> def = new LinkedHashMap<String, Object>();

		def.put(ApiKeys.target, probe.getTarget());
		def.put(ApiKeys.ip_version, probe.getIp_version());
		def.put(ApiKeys.timeout, probe.getTimeout());
		def.put(ApiKeys.dcp, probe.getDescription());
		def.put(ApiKeys.protocol, probe.getProtocol());
		def.put(ApiKeys.resolve, probe.isResolve_on_probe());
		def.put(ApiKeys.packets, probe.getPackets());
		def.put(ApiKeys.size, probe.getSize());
		def.put(ApiKeys.paris, probe.getParis());
		def.put(ApiKeys.firstHop, probe.getFirsthop());
		def.put(ApiKeys.dstOption, probe.getDestination_option_size());
		def.put(ApiKeys.frag, probe.isDontfrag());
		def.put(ApiKeys.type, probe.getType());

		probeDescription.add(def);

		for (Probe currentProbe : probe.getList_of_probes()) {
			JSONObject jsonProbe = new JSONObject();
			jsonProbe.put(ApiKeys.tags, currentProbe.getTags());
			jsonProbe.put(ApiKeys.type, currentProbe.getType());
			jsonProbe.put(ApiKeys.value, currentProbe.getValue());
			jsonProbe.put(ApiKeys.requested, currentProbe.getRequested());
			probes.add(jsonProbe);
		}

		json.put(ApiKeys.def, probeDescription);
		json.put(ApiKeys.probes, probes);
		json.put(ApiKeys.oneoff, probe.isIs_oneoff());
		jsonString = json.toString();
	}

}
