package com.ece.springboot.jwt.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("data")
public class SampleController {

	@GetMapping("account")
	public String getRequest(@PathParam("accountNumber") String accountNumber, @PathParam("pageId") String pageId) {

		// plain request

		String concated = new StringBuilder().append("/data/account").append("::")
				.append("accountNumber=" + accountNumber).append("pageId=" + pageId).toString();

		String requestStrOri = "{\n" + "  \"cashBlock\": {\n" + "    \"accountCurrency\": \"INR\",\n"
				+ "    \"accountCurrencyAmount\": 10,\n" + "    \"autoReleaseFlag\": 2,\n"
				+ "    \"blockAdvanceFlag\": 2,\n" + "    \"blockCurrency\": \"INR\",\n" + "    \"blockAmount\": 10,\n"
				+ "    \"blockLevel\": 1,\n" + "    \"blockPurpose\": 5,\n" + "    \"customerId\": 102117,\n"
				+ "    \"releaseDate\": \"\",\n" + "    \"remarks\": \"ASBA-API-0102202301\",\n"
				+ "    \"identifierValue\": \"\",\n" + "    \"accountReference\": \"%s\",\n"
				+ "    \"transactionReference\": \"%s\"\n" + "  }\n" + "}";

		String requestStr = String.format(requestStrOri, "1232323Nagendta", "trnxtdscyddf");

		String ppstReqiuest = new StringBuilder().append("/data/createcashBlock").append("::").append(requestStrOri)
				.toString();

		System.out.println("the concated data" + concated);
		
		PrivateKey priateKey = null;

		// hashing

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(concated.getBytes(StandardCharsets.UTF_8));

			StringBuilder hexString = new StringBuilder();

			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}

			System.out.println("the hased string " + hexString);

			// prpare jwt token

			String jwtToken = this.createJWTAndSign(concated, concated, concated, concated, concated, concated,
					concated, accountNumber, pageId, priateKey, hexString.toString());
			
			System.out.println("the jwt token "+jwtToken);

			String accountVerifyUrl = "http://localhost:8080/data/acco7nnnnnn";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("entity", "");
			headers.add("languageCode", "1");
			headers.add("userId", "12");
			headers.add("AccessToken", jwtToken);
			headers.add("InitiatingSystem", "TJSB");

			// GET

			// HttpEntity<String> entity = new HttpEntity<String>(null, headers);

			//

			HttpEntity<String> entity = new HttpEntity<String>(requestStr, headers);

			RestTemplate resttemplate = new RestTemplate();

			ResponseEntity<String> responseEntity = resttemplate.exchange(accountVerifyUrl.trim(), HttpMethod.GET,
					entity, String.class);
			String responseStr = responseEntity.getBody();

			System.out.println("the response string " + responseStr);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(" the accountNukber :::::::" + accountNumber);

		return "Dara";

	}

	private String createJWTAndSign(String issuer, String subject, String server, String deviceid, String appversion,
			String os, String userType, String clientid, String pin, PrivateKey privateKey, String hashData)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

		JwtBuilder builder = Jwts.builder().claim("request-id", "R1").claim("from-system", "internal-jwt-issuer")
				.claim("by-user", "Rest").claim("password", "XXXX").claim("Business-data-hash", hashData)
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
				.setNotBefore(new Date()).setHeaderParam("kid", "bancs-restapi-key").setHeaderParam("alg", "RS256")
				.signWith(signatureAlgorithm, privateKey);

		return builder.compact();

	}

}
