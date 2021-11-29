package com.Snippitz.snipzapp.controller;

import com.Snippitz.snipzapp.dto.CreateUserDto;

import com.Snippitz.snipzapp.dto.UserLoginDto;
import com.Snippitz.snipzapp.entity.ConnectionMessage;
import com.Snippitz.snipzapp.entity.SnipUser;

import com.Snippitz.snipzapp.entity.VerifyTokenMessage;
import com.Snippitz.snipzapp.helpers.JwtHelper;
import com.Snippitz.snipzapp.repository.UserRepository;
import com.Snippitz.snipzapp.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    private final JwtHelper jwtHelper;
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(JwtHelper jwtHelper, UserService userService, UserRepository userRepository) {
        this.jwtHelper = jwtHelper;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/test/users/{username}")
    public ResponseEntity<SnipUser> getSnipUser(@PathVariable String username){

        SnipUser snipUser = this.userService.findUser(username);

        return ResponseEntity.ok(snipUser);
    }


    @PostMapping("/api/test/users/register")
    public ResponseEntity<SnipUser> testUserRegisterJwt( @RequestBody CreateUserDto createUserDto){
        SnipUser snipUser = userService.registerUserJwt(createUserDto);
        return  ResponseEntity.ok(snipUser);

    }

    @PostMapping("/api/test/users/login")
    public ResponseEntity<?> testLoginUser(@RequestHeader HttpHeaders httpHeaders, @RequestBody UserLoginDto userLogin){
        String token2 = "";
        HttpHeaders headers = new HttpHeaders();
        SnipUser snipUser = this.userService.logInUserJwt(userLogin);
            try {
                token2 = jwtHelper.createJwtToken(userLogin.getSnipUsername());

            } catch (JWTCreationException exception){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Access-Control-Expose-Headers", "authorization");
        headers.add("Authorization", "Bearer "+ token2 );
        return  ResponseEntity.ok()
                .headers(headers)
                .body(snipUser);
    }

    @PostMapping("/api/test/user/verify")
    public ResponseEntity<?> verifytoken(@RequestHeader HttpHeaders httpHeaders){
        //read the authorization headers
        String token = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        //set empty username varbale
        String snipUsername = "";
        String realToken = token.substring(7);
        //verify the jwt
        snipUsername = jwtHelper.verifyJwtToken(realToken);
       //return response
        return ResponseEntity.ok(new VerifyTokenMessage("valid", snipUsername));
    }

}
