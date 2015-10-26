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

/**
 * Calculates a unique ID from two given long values. The calculation is based
 * on: https://en.wikipedia.org/wiki/Pairing_function
 *
 */
public class UniqueID {

	public static long getNewID(long a, long b) {
		if (a < b) {
			long temp = a;
			a = b;
			b = temp;
		}
		return (long) (0.5 * (a + b) * (a + b + 1) + b);
	}

}
