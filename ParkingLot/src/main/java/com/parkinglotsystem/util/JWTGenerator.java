package com.parkinglotsystem.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTGenerator {
	private JWTGenerator() {

	}

	public static String createJWT(Long id, long ttlMillis) {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Util.SECRET_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		JwtBuilder builder = Jwts.builder().setId(String.valueOf(id)).setIssuedAt(now).setSubject(Util.SUBJECT)
				.setIssuer(Util.ISSUER).signWith(signatureAlgorithm, signingKey);

		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		return builder.compact();
	}

	public static Long decodeJWT(String jwt) {

		return Long.parseLong(Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Util.SECRET_KEY))
				.parseClaimsJws(jwt).getBody().getId());

	}
}
