package com.dxc.mts.api.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author mkhan339
 *
 */
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 10 * 60;

	@Value("${mts.jwt.secret}")
	private String secret;

	/**
	 * This method is created to get username from jwt token
	 * 
	 * @param token holds the information of the token
	 * @return username
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * This method is created to get the expiration date from the jwt token
	 * 
	 * @param token holds the information of the token
	 * @return expire time
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * 
	 * @param <T>
	 * @param token          holds the information of the token
	 * @param claimsResolver holds the information of the claimsResolver
	 * @return
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	//
	/**
	 * This method is created to retrieving any information from token we will need
	 * the secret key
	 * 
	 * @param token holds the information of the token
	 * @return claims
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * This method is created to get the expire date
	 * 
	 * @param token holds the information of the token
	 * @return true/false
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * This method is created to generate the token for user
	 * 
	 * @param userDetails holds the information of the user details
	 * @return
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	/**
	 * This method is created to get the token with token, like Issuer, Expiration,
	 * Subject, and the ID and with HS512 algorithm
	 * 
	 * @param claims  holds the information of the claims
	 * @param subject holds the information of the subject
	 * @return token return generated token
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * This method is created to validate the token
	 * 
	 * @param token       holds the information of the token
	 * @param userDetails holds the information of the user details
	 * @return true/false
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}