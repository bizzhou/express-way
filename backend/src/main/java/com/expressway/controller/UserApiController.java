package com.expressway.controller;

import com.expressway.jwt.JwtUtil;
import com.expressway.model.Customer;
import com.expressway.model.User;

import com.expressway.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

import static com.expressway.jwt.JwtUtil.USER_ID;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserApiController {

    public static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/api/protected")
    public @ResponseBody
    Object hellWorld(@RequestHeader(value = USER_ID) String userId) {
        return "Hello World! This is a protected api, your use id is " + userId;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map> login(@RequestBody final User credentials) throws IOException {
        Map result;

        logger.info("********************************************************************************");

        if ((result = userService.validateUser(credentials)) != null) {

            logger.debug((String) result.get("role"));
            logger.debug((String) result.get("username"));
            String jwt = JwtUtil.generateToken(result.get("role").toString() + "::" + result.get("username").toString());

            logger.debug(jwt);
            result.put("token", jwt);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } else

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value="signup", method = RequestMethod.POST)
    public ResponseEntity<Boolean> signup(@RequestBody final Customer form) throws IOException{

        if(userService.addUser(form) == true){
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);

    }


    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") int userId){

        if(userService.deleteUser(userId))
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);

        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateUser() throws IOException{

        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }

}
