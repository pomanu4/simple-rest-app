package ua.com.company.component;

import java.nio.charset.StandardCharsets;

import org.springframework.security.crypto.codec.Base64;

public class CreateCryptedHeader {

	public static String getSecyredHesder(String name, String password) {
		String token = name + ":" + password;
		String codedToken = new String(Base64.encode(token.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);

		return codedToken;
	}

}
