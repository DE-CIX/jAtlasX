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
package net.decix.jatlasx.ripe.atlas.api.measurement;

public class ApiKeyException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ApiKeyException() {
		super();
	}

	public ApiKeyException(String message) {
		super(message);
	}
}
