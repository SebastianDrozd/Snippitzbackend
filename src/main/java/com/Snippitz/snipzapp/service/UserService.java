package com.Snippitz.snipzapp.service;

import com.Snippitz.snipzapp.entity.ConnectionMessage;
import com.Snippitz.snipzapp.entity.SnipUser;

import com.Snippitz.snipzapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(SnipUser user){
        this.userRepository.save(user);
    }

    public ConnectionMessage login(SnipUser user){
        SnipUser user2 = this.userRepository.findSnipUserBySnipUsername(user.getSnipUsername());
        if(user2.getSnipPassword().equals(user.getSnipPassword()))
                return new ConnectionMessage("Connected",user.getSnipUsername());
        else
           return new ConnectionMessage("not connected","");
    }
}
