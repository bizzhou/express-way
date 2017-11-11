package com.expressway.controller;

import com.expressway.jwt.JwtUtil;
import com.expressway.model.SignUp;
import com.expressway.model.User;
import com.expressway.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.expressway.jwt.JwtUtil.USER_ID;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserApiController {

    public static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/api/protected")
    public @ResponseBody
    Object hellWorld(@RequestHeader(value = USER_ID) String userId) {
        return "Hello World! This is a protected api, your use id is " + userId;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map> login(@RequestBody final User credentials) throws IOException {
        String role;
        if ((role = userService.validateUser(credentials)) != null) {
            String jwt = JwtUtil.generateToken(credentials.getUsername(), role);

            logger.debug("___________________________________");
            logger.debug(jwt);
            logger.debug(role);
            logger.debug(role);

            Map<String, String> result = new HashMap<>();
            result.put("token", jwt);
            result.put("role", role);

            return new ResponseEntity<Map>(result, HttpStatus.OK);

        } else

            return new ResponseEntity<Map>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value="signup", method = RequestMethod.POST)
    public ResponseEntity<Boolean> signup(@RequestBody final SignUp form) throws IOException{

        if(userService.addUser(form) == true){


            return new ResponseEntity<Boolean>(true, HttpStatus.OK);

        }
        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);

    }


}
