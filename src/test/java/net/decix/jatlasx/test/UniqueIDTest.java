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
package net.decix.jatlasx.test;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import junit.framework.TestCase;
import net.decix.jatlasx.data.UniqueID;

public class UniqueIDTest extends TestCase {

	@Test
	public void testUniqueID() {
		long a = 512123L;
		long b = 123124L;
		long c = 4346L;
		assertEquals(UniqueID.getNewID(a, b), UniqueID.getNewID(b, a));
		assertNotEquals(UniqueID.getNewID(c, b), UniqueID.getNewID(b, a));
	}

	@Test
	public void testUniqueIDAdvanced() {

		List<Long> longList = new ArrayList<Long>();
		List<Long> finalLongList = new ArrayList<Long>();
		// steps must be > 1000 and should be < 5000 until the test takes to
		// long time
		int steps = 5000;

		for (int i = 0; i < steps; i++) {
			long longSingle = (Long.valueOf(ThreadLocalRandom.current().nextInt()));
			longList.add(longSingle);
		}

		for (int i = 0; i < steps; i++) {
			long longSingle = longList.get(i);
			assertEquals(UniqueID.getNewID(longSingle, i % steps), UniqueID.getNewID(i % steps, longSingle));
			long newLongSingle0 = UniqueID.getNewID(longSingle, i % steps);
			long newLongSingle1 = UniqueID.getNewID(longSingle, (i - 1) % steps);
			long newLongSingle2 = UniqueID.getNewID(longSingle, (i + 1) % steps);
			assertNotEquals(newLongSingle1, newLongSingle2);
			finalLongList.add(newLongSingle0);
			finalLongList.add(newLongSingle1);
			finalLongList.add(newLongSingle2);
			long newLongSingle3 = UniqueID.getNewID(longSingle, (i - 2) % steps);
			long newLongSingle4 = UniqueID.getNewID(longSingle, (i + 2) % steps);
			long newLongSingle5 = UniqueID.getNewID(longSingle, (i - 3) % steps);
			long newLongSingle6 = UniqueID.getNewID(longSingle, (i + 4) % steps);
			long newLongSingle7 = UniqueID.getNewID(longSingle, (i - 5) % steps);
			long newLongSingle8 = UniqueID.getNewID(longSingle, (i + 6) % steps);
			finalLongList.add(newLongSingle3);
			finalLongList.add(newLongSingle4);
			finalLongList.add(newLongSingle5);
			finalLongList.add(newLongSingle6);
			finalLongList.add(newLongSingle7);
			finalLongList.add(newLongSingle8);
		}

		int i = 0;
		int size = finalLongList.size();

		for (long finalSingle : finalLongList) {
			i++;
			for (long checkSingle : finalLongList) {
				if (Long.compare(checkSingle, finalSingle) == 0) {
					i++;
				}

			}
		}
		assertEquals(i / 2, size);
	}
}
