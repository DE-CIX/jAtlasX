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
package net.decix.jatlasx.ripe.atlas.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpSubmitter {

	private String url;
	private String apiKey;
	private HttpClient httpClient;

	public HttpSubmitter(String url, String apiKey) {
		this.url = url;
		this.apiKey = apiKey;
		httpClient = HttpClientBuilder.create().build(); // Use this instead

		// Special property to avoid handshake alert: unrecognized_name
		System.setProperty("jsse.enableSNIExtension", "false");
	}

	public String get(String parameter) {
		HttpGet request = new HttpGet(url + parameter);
		HttpResponse response = null;
		String responseString = null;
		try {
			request.addHeader("accept", "application/json");
			response = httpClient.execute(request);
			responseString = handleResponse(response);

		} catch (IOException e) {
			String errorMsg = "could not perform get request with parameter: " + parameter;
			System.err.println(e.getClass().getName() + ":" + errorMsg + " (" + this.getClass().getName() + ")");
		} finally {
			request.releaseConnection();
		}

		return responseString;
	}

	public String submit(String body) {
		String submitUrl;

		if (apiKey != "") {
			submitUrl = url + "?key=" + apiKey;
		} else {
			submitUrl = url;
		}
		HttpPost request = new HttpPost(submitUrl);
		String responseString = null;
		try {

			StringEntity params = new StringEntity(body);
			request.addHeader("content-type", "application/json");
			request.addHeader("accept", "application/json");
			request.setEntity(params);

			HttpResponse response = httpClient.execute(request);
			responseString = handleResponse(response);

		} catch (IOException e) {
			String errorMsg = "Fatal transport error: " + e.getMessage();
			System.err.println(e.getClass().getName() + ":" + errorMsg + " (" + this.getClass().getName() + ")");
		} finally {
			request.releaseConnection();
		}

		return responseString;
	}

	private String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
		return new BasicResponseHandler().handleResponse(response);
	}

}
