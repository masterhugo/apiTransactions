package co.neoris.apitransactions.util;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import co.neoris.apitransactions.enums.ErrorEnum;
import co.neoris.apitransactions.exceptions.ApiException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	public static long accessTokenTimeOut; // 60 segundos * 30 minutos
	
	public static String accessTokenSecret;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(accessTokenSecret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(String name) {

		Claims claims = Jwts.claims().setSubject(name);
		claims.put("admin", "true");
		claims.put("name",name);

		return Jwts.builder().setClaims(claims).setIssuer("https://site.com")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + accessTokenTimeOut * 1000))
				.signWith(SignatureAlgorithm.HS256, accessTokenSecret).compact();
	}


	public Claims validateToken(String token) {
		try {
			final String username = getUsernameFromToken(token);
			Claims claims = getAllClaimsFromToken(token);
			if (!isTokenExpired(token)) {
				return claims;
			}
		} catch (Exception e) {
			throw new ApiException(ErrorEnum.UNAUTHORIZED,e.getMessage());
		}
		return null;
	}
	

	public static String getBody(String jwtToken) {
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        String header = new String(Base64.getDecoder().decode(base64EncodedHeader));
        String body = new String(Base64.getDecoder().decode(base64EncodedBody));
        
        return body;
	}
	
	public static String getBase64(String data) {
		return Base64.getEncoder().encodeToString(data.getBytes());
	}

	
}


