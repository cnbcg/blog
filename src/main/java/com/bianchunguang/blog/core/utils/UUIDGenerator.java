package com.bianchunguang.blog.core.utils;

import java.util.UUID;

public class UUIDGenerator {

	// non-instantiable
	private UUIDGenerator() {
	}

	/**
	 * Generate a new UUID String.
	 */
	public static UUID generateUUID() {
		return UUID.randomUUID();
	}
	
	public static UUID valueOf(String uuidString) {
		return UUID.fromString(uuidString);
	}
	
//	public static void main(String... args) {
//		System.out.println(UUIDGenerator.generateUUID());
//	}

}
