/*
 * This file is part of jAtlasX
 *
 * Copyright (c) 2015 DE-CIX Management GmbH <http://www.de-cix.net> - All rights
 * reserved.
 * 
 * @author: Florian Kaufmann <florian.kaufmann@de-cix.net>
 *
 * This software is licensed under the Apache License, version 2.0. A copy of 
 * the license agreement is included in this distribution.
 */
package net.decix.jatlasx.test;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.junit.Test;

import junit.framework.TestCase;
import net.decix.jatlasx.data.IpAddress;
import net.decix.jatlasx.data.TraceroutePath;
import net.decix.jatlasx.ripe.atlas.api.MeasurementGathering;
import net.decix.jatlasx.ripe.atlas.api.handler.MeasurementIDHandler;
import net.decix.jatlasx.ripe.atlas.api.handler.ProbeHandler;
import net.decix.jatlasx.ripe.atlas.api.handler.ProbeListHandler;
import net.decix.jatlasx.ripe.atlas.api.handler.ResponseHandler;
import net.decix.jatlasx.ripe.atlas.api.handler.TraceHandler;
import net.decix.jatlasx.ripe.atlas.api.handler.TracerouteHandler;
import net.decix.jatlasx.ripe.atlas.api.handler.TracesHandler;
import net.decix.jatlasx.ripe.atlas.json.Probe;

public class HandlerTest extends TestCase {

	private final boolean PRINT_RESULTS = false;

	@Test
	public void testHandler_Interface() {

		ResponseHandler<Long> handler1 = new MeasurementIDHandler();
		ResponseHandler<IpAddress> handler2 = new ProbeHandler();
		ResponseHandler<Probe> handler3 = new ProbeListHandler(2337);
		ResponseHandler<IpAddress> handler4 = new TraceHandler();
		ResponseHandler<TraceroutePath> handler5 = new TracerouteHandler();
		ResponseHandler<IpAddress> handler6 = new TracesHandler();

		assertEquals(handler1.getClass().getSimpleName(), "MeasurementIDHandler");
		assertEquals(handler2.getClass().getSimpleName(), "ProbeHandler");
		assertEquals(handler3.getClass().getSimpleName(), "ProbeListHandler");
		assertEquals(handler4.getClass().getSimpleName(), "TraceHandler");
		assertEquals(handler5.getClass().getSimpleName(), "TracerouteHandler");
		assertEquals(handler6.getClass().getSimpleName(), "TracesHandler");
	}

	@Test
	public void testMeasurementIDHandler() {

		boolean receivedData = false;
		ResponseHandler<Long> handler = new MeasurementIDHandler();

		JSONArray jsonTestArrayLong = new JSONArray();
		jsonTestArrayLong.put(new Long(10));
		jsonTestArrayLong.put(new Long(100));
		jsonTestArrayLong.put(new Long(1000));
		jsonTestArrayLong.put(new Long(10000));
		jsonTestArrayLong.put(new Long(0));

		JSONObject obj = new JSONObject();
		obj.put("measurements", jsonTestArrayLong);
		obj.put("num", new Integer(100));
		obj.put("balance", new Double(1000.21));
		obj.put("is_vip", new Boolean(true));
		obj.put("nickname", new Object());

		String definitions = new JSONStringer().array().object().key("target").value("192.168.178.1").key("af")
				.value("4").endObject().object().key("newline").value("123").key("newline2").value("456").endObject()
				.endArray().toString();

		String probes = new JSONStringer().array().object().key("value").value("21931").key("type").value("probes")
				.key("requested").value("1").endObject().endArray().toString();

		String myString = new JSONStringer().object().key("definitions").value(definitions).key("probes").value(probes)
				.key("measurements").value(jsonTestArrayLong).key("is_oneoff").value(false).endObject().toString();

		List<Long> list = handler.handleResponse(myString);
		if (!list.isEmpty()) {
			if (PRINT_RESULTS) {
				System.out.println("TEST (HandlerTest) - MeasurementIDHandler " + list);
			}
			receivedData = true;
		}
		assertTrue(receivedData);
	}

	@Test
	public void testProbeHandlerandProbeListHandler() {

		boolean receivedData = false;
		ResponseHandler<IpAddress> handlerProbe = new ProbeHandler();

		JSONObject jsonTestObjectProbe1 = new JSONObject();
		jsonTestObjectProbe1.put("address_v4", "192.168.1.1");
		jsonTestObjectProbe1.put("id", 11111111);
		jsonTestObjectProbe1.put("is_public", true);
		jsonTestObjectProbe1.put("asn_v4", 12345);
		jsonTestObjectProbe1.put("status_name", "Connected");

		JSONObject jsonTestObjectProbe2 = new JSONObject();
		jsonTestObjectProbe2.put("address_v4", "10.10.1.1");
		jsonTestObjectProbe2.put("id", 22222222);
		jsonTestObjectProbe2.put("is_public", true);
		jsonTestObjectProbe2.put("asn_v4", 12345);
		jsonTestObjectProbe2.put("status_name", "Connected");

		JSONArray jsonTestArrayObjects = new JSONArray();
		jsonTestArrayObjects.put(jsonTestObjectProbe1);
		jsonTestArrayObjects.put(jsonTestObjectProbe2);

		String myString = new JSONStringer().object().key("objects").value(jsonTestArrayObjects).key("is_oneoff")
				.value(false).endObject().toString();

		List<IpAddress> list = handlerProbe.handleResponse(myString);
		if (!list.isEmpty()) {
			if (PRINT_RESULTS) {
				System.out.println("TEST (HandlerTest) - ProbeHandler " + list);
			}
			receivedData = true;
		}
		assertTrue(receivedData);
		receivedData = false;

		ResponseHandler<Probe> handlerProbeList = new ProbeListHandler(12345);

		List<Probe> listProbe = handlerProbeList.handleResponse(myString);
		if (!listProbe.isEmpty()) {
			if (PRINT_RESULTS) {
				System.out.println("TEST (HandlerTest) - ProbeListHandler " + listProbe);
			}
			receivedData = true;
		}
		assertTrue(receivedData);
	}

	@Test
	public void testTraceHandler() {

		boolean receivedData = false;
		ResponseHandler<IpAddress> handlerPseudo = new TraceHandler();
		// Pseudo Data
		JSONObject jsonTestObjectProbe1 = new JSONObject();
		jsonTestObjectProbe1.put("from", "192.168.1.2");
		jsonTestObjectProbe1.put("id", 11111111);
		jsonTestObjectProbe1.put("is_public", true);
		jsonTestObjectProbe1.put("asn_v4", 12345);
		jsonTestObjectProbe1.put("status_name", "Connected");

		JSONObject jsonTestObjectProbe2 = new JSONObject();
		jsonTestObjectProbe2.put("from", "10.10.1.2");
		jsonTestObjectProbe2.put("id", 22222222);
		jsonTestObjectProbe2.put("is_public", true);
		jsonTestObjectProbe2.put("asn_v4", 12345);
		jsonTestObjectProbe2.put("status_name", "Connected");

		String myString = new JSONStringer().array().value(jsonTestObjectProbe1).value(jsonTestObjectProbe2)
				.value("is_oneoff").value(false).endArray().toString();

		List<IpAddress> list1 = handlerPseudo.handleResponse(myString);
		if (!list1.isEmpty()) {
			if (PRINT_RESULTS) {
				System.out.println("TEST (HandlerTest) - TraceHandler (Pseudo) " + list1);
			}
			receivedData = true;
		}
		assertTrue(receivedData);

		receivedData = false;
		ResponseHandler<IpAddress> handlerReal = new TraceHandler();

		// Real Data
		String result = MeasurementGathering.getRawMeasurementResultsbyID(2074680);

		List<IpAddress> list2 = handlerReal.handleResponse(result);
		if (!list2.isEmpty()) {
			if (PRINT_RESULTS) {
				System.out.println("TEST (HandlerTest) - TraceHandler (Real) " + list2);
			}
			receivedData = true;
		}
		assertTrue(receivedData);

	}

	// @Test
	public void testTracerouteHandler() {

		boolean receivedData = false;
		ResponseHandler<TraceroutePath> handlerPseudo = new TracerouteHandler();
		// Pseudo Data
		JSONObject jsonTestResult1 = new JSONObject();
		jsonTestResult1.put("from", "192.168.1.1");
		JSONObject jsonTestResult2 = new JSONObject();
		jsonTestResult2.put("from", "192.168.1.2");

		JSONArray jsonTestArrayObject1 = new JSONArray();
		jsonTestArrayObject1.put(jsonTestResult1);
		jsonTestArrayObject1.put(jsonTestResult2);

		JSONObject jsonTestObject1 = new JSONObject();
		jsonTestObject1.put("result", jsonTestArrayObject1);

		JSONArray jsonTestArrayObject2 = new JSONArray();
		jsonTestArrayObject2.put(jsonTestObject1);

		JSONObject jsonTestObject2 = new JSONObject();
		jsonTestObject2.put("from", "192.168.1.3");
		jsonTestObject2.put("id", 11111111);
		jsonTestObject2.put("is_public", true);
		jsonTestObject2.put("asn_v4", 12345);
		jsonTestObject2.put("status_name", "Connected");
		jsonTestObject2.put("dst_name", "192.168.1.4");
		jsonTestObject2.put("result", jsonTestArrayObject2);

		String myString = new JSONStringer().array().value(jsonTestObject2).endArray().toString();

		List<TraceroutePath> list1 = handlerPseudo.handleResponse(myString);
		if (!list1.isEmpty()) {
			if (PRINT_RESULTS) {
				System.out.println("TEST (HandlerTest) - TracerouteHandler (Pseudo) " + list1);
			}
			receivedData = true;
		}
		assertTrue(receivedData);

		receivedData = false;
		ResponseHandler<TraceroutePath> handlerReal = new TracerouteHandler();

		// Real Data
		String result = MeasurementGathering.getRawMeasurementResultsbyID(2074680);
		List<TraceroutePath> list2 = handlerReal.handleResponse(result);
		if (!list2.isEmpty()) {
			if (PRINT_RESULTS) {
				System.out.println("TEST (HandlerTest) - TracerouteHandler (Real) " + list2);
			}
			receivedData = true;
		}
		assertTrue(receivedData);
	}

}
