package com.Snippitz.snipzapp.helpers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class JwtHelper {

    public String createJwtToken(String username) throws JWTCreationException {

        //initliazie the empty token String;
        String token = "";

        Algorithm algorithm = Algorithm.HMAC256("secret");
        HashMap<String,String> map = new HashMap<>();
        map.put("username",username);
        token = JWT.create().withClaim("username",username)
                .withIssuer("auth0")
                .sign(algorithm);

        return token;
    }

    public String verifyJwtToken(String token){
        String snipUsername = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt.getClaim("username"));
            Claim username = jwt.getClaim("username");
            System.out.println("claim:" + username);
            snipUsername =  username.toString();
            snipUsername =snipUsername.substring(1);
            snipUsername = snipUsername.replace("\"","");
            System.out.println(snipUsername);
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Error verifying token");
        }


        return snipUsername;
    }
}
