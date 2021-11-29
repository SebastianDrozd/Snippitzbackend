package com.Snippitz.snipzapp.service;

import com.Snippitz.snipzapp.dto.CreateUserDto;
import com.Snippitz.snipzapp.dto.UserLoginDto;
import com.Snippitz.snipzapp.entity.ConnectionMessage;
import com.Snippitz.snipzapp.entity.SnipUser;

import com.Snippitz.snipzapp.error.ResourceNotFoundException;
import com.Snippitz.snipzapp.error.UserAlreadyExistsException;
import com.Snippitz.snipzapp.error.WrongPasswordException;
import com.Snippitz.snipzapp.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public SnipUser registerUserJwt(CreateUserDto createUserDto) {
        if(userRepository.existsBySnipUsername(createUserDto.getSnipUsername())){
            throw new UserAlreadyExistsException("The username you chose already exists, please choose a different username");
        }
        //hash password
        String hashed = BCrypt.hashpw(createUserDto.getSnipPassword(),BCrypt.gensalt());
        //set dto password so its hased
        createUserDto.setSnipPassword(hashed);

        //save user to database
        SnipUser snipUser = SnipUser
                .builder()
                .snipUsername(createUserDto.getSnipUsername())
                .snipPassword(createUserDto.getSnipPassword())
                .createdAt(createUserDto.getCreatedAt())
                .build();
       return this.userRepository.save(snipUser);
    }


    public SnipUser logInUserJwt(UserLoginDto userLoginDto){
        //check if the user exists in the database
        if(!userRepository.existsBySnipUsername(userLoginDto.getSnipUsername())){
            throw new ResourceNotFoundException("No user exists with that username");
        }
        else{
            //create the user entity model
            SnipUser snipUser = this.userRepository.findSnipUserBySnipUsername(userLoginDto.getSnipUsername());

            //compare password
            if (BCrypt.checkpw(userLoginDto.getSnipPassword(), snipUser.getSnipPassword())) {
                System.out.println("The password matches.");
                return snipUser;
            }
            else{
                System.out.println("Wrong password combination");
                throw new WrongPasswordException("Wrong password");
            }
        }



    }

    public SnipUser findUser(String username) {
        return this.userRepository.findSnipUserBySnipUsername(username);
    }
}
