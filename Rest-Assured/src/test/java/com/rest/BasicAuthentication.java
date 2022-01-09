package com.rest;

import java.util.Base64;

public class BasicAuthentication {
	public static void main(String[] args) {
		String userNameColonPassword = "";
		String base64Encoded = Base64.getEncoder().encodeToString(userNameColonPassword.getBytes());
		System.out.println("Encoded: " +base64Encoded);
		byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);
		System.out.println("Decoded: " +new String(decodedBytes));
	}

}
