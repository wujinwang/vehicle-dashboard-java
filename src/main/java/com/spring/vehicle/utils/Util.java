package com.spring.vehicle.utils;

import java.util.UUID;
import java.util.stream.Stream;

public class Util {

	private Util() {
		throw new UnsupportedOperationException("Cannot instantiate a Util class");
	}

	public static String generateRandomUuid() {
		return UUID.randomUUID().toString();
	}

	public static void main (String[] args) {
		Stream.iterate(0, i -> i + 1)
			  .filter(i -> i % 2 == 0)
			  .limit(10)
			  .forEach(System.out::println);
	}
}
