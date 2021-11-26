package com.Snippitz.snipzapp.controller;

import com.Snippitz.snipzapp.dto.CreateUserDto;

import com.Snippitz.snipzapp.dto.UserLoginDto;
import com.Snippitz.snipzapp.entity.ConnectionMessage;
import com.Snippitz.snipzapp.entity.SnipUser;

import com.Snippitz.snipzapp.entity.VerifyTokenMessage;
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

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/api/users/register")
    public String registerUser(@RequestBody SnipUser user){
        System.out.println(user);
        userService.registerUser(user);
        return "success";
    }

    @PostMapping("/api/users/login")
    public ConnectionMessage loginUser(@RequestBody SnipUser user){
       return userService.login(user);
    }


    @PostMapping("/api/test/users/register")
    public ResponseEntity<SnipUser> testUserRegisterJwt(@RequestHeader HttpHeaders httpHeaders, @RequestBody CreateUserDto createUserDto){

        if(userRepository.existsBySnipUsername(createUserDto.getSnipUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        String token2 = "";
        //hash password
       String hashed = BCrypt.hashpw(createUserDto.getSnipPassword(),BCrypt.gensalt());
        //set dto password so its hased
        createUserDto.setSnipPassword(hashed);

        //save user to database
        SnipUser snipUser = SnipUser.builder().snipUsername(createUserDto.getSnipUsername()).snipPassword(createUserDto.getSnipPassword()).build();
        this.userService.registerUser(snipUser);

        //generate the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer "+ token2 );
        return  ResponseEntity.ok()
                .headers(headers)
                .body(snipUser);
    }

    @PostMapping("/api/test/users/login")
     public ResponseEntity<?> testLoginUser(@RequestHeader HttpHeaders httpHeaders, @RequestBody UserLoginDto userLogin){
       // String token3 = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        //System.out.println(token3);
        //String realToken = token3.substring(7);
        //System.out.println(realToken);
        //System.out.println("user login" + userLogin.getUsername());

        //check if the user exists in the database
        if(!userRepository.existsBySnipUsername(userLogin.getSnipUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        //create the user entity model
        SnipUser snipUser = this.userRepository.findSnipUserBySnipUsername(userLogin.getSnipUsername());
        //compare password
        if (BCrypt.checkpw(userLogin.getSnipPassword(), snipUser.getSnipPassword())) {
            System.out.println("The password matches.");
            //create the jwt token
            String token2 = "";
            try {
                Algorithm algorithm = Algorithm.HMAC256("secret");
                HashMap<String,String> map = new HashMap<>();
                map.put("username",userLogin.getSnipUsername());
                token2 = JWT.create().withClaim("username",userLogin.getSnipUsername())
                        .withIssuer("auth0")
                        .sign(algorithm);
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                headers.add("Access-Control-Expose-Headers", "authorization");
                headers.add("Authorization", "Bearer "+ token2 );
                return  ResponseEntity.ok()
                        .headers(headers)
                        .body(snipUser);
            } catch (JWTCreationException exception){
                //Invalid Signing configuration / Couldn't convert Claims.
            }
        }
        else{
            System.out.println("The password does not match.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return null;
    }

    @PostMapping("/api/test/user/verify")
    public ResponseEntity<?> verifytoken(@RequestHeader HttpHeaders httpHeaders){

        String token = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        String snipUsername = "";
        String realToken = token.substring(7);
        System.out.println(realToken);
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(realToken);

            System.out.println(jwt.getClaim("username"));
            Claim username = jwt.getClaim("username");
            System.out.println("claim:" + username);
           snipUsername =  username.toString();
            snipUsername =snipUsername.substring(1);
            snipUsername = snipUsername.replace("\"","");
            System.out.println(snipUsername);
        } catch (JWTVerificationException exception){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new VerifyTokenMessage("valid", snipUsername));
    }

}
