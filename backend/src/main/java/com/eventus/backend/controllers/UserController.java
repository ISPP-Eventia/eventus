package com.eventus.backend.controllers;

import com.eventus.backend.models.User;
import com.eventus.backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController extends ValidationController{
    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/session/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> body){
        String email=body.get("email");
        String password=body.get("password");
        String token=userService.login(email,password).orElse(null);
        User user = userService.findByEmail(email).orElse(null);
        if(token!=null&&user!=null){
            return ResponseEntity.accepted().body("{token:"+token+", id: "+user.getId()+"}");
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/session/register")
    public ResponseEntity<String> register(@RequestBody @Valid User user){

        if(this.userService.findByEmail(user.getEmail()).isEmpty()){
            String password=user.getPassword();
            userService.saveUser(user);
            String token=userService.login(user.getEmail(),password).orElse(null);
            if(token!=null){
                return ResponseEntity.accepted().body("{token:"+token+", id: "+user.getId()+"}");
            }
        }

        return ResponseEntity.status(401).build();

    }

}
