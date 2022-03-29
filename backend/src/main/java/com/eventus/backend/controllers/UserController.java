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
    public ResponseEntity<Map<String,Object>> login(@RequestBody Map<String,String> body){
        String email=body.get("email");
        String password=body.get("password");
        String token=userService.login(email,password).orElse(null);
        User user = userService.findByEmail(email).orElse(null);
        if(token!=null&&user!=null){
            Map<String,Object> res=Map.of("token",token,"id",user.getId());
            return ResponseEntity.accepted().body(res);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/session/signup")
    public ResponseEntity<Map<String,Object>> register(@RequestBody @Valid User user){
        ResponseEntity<Map<String,Object>> response=null;
        Map<String, Object> res;
        if(this.userService.findByEmail(user.getEmail()).isEmpty()){
            String password=user.getPassword();
            userService.saveUser(user);
            String token=userService.login(user.getEmail(),password).orElse(null);

            if(token!=null){
                res = Map.of("token", token, "id", user.getId());
                response = ResponseEntity.accepted().body(res);
            }else{
                res = Map.of("error", "Error getting token");
                response = ResponseEntity.badRequest().body(res);
            }

        }else{
            res = Map.of("error", "User already exits");
            response = ResponseEntity.badRequest().body(res);
        }
        return response;

    }

}
