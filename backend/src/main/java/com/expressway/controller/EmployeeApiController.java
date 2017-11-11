package com.expressway.controller;

import com.expressway.jwt.JwtUtil;
import com.expressway.model.User;
import com.expressway.service.impl.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EmployeeApiController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    public static final Logger logger = LoggerFactory.getLogger(UserApiController.class);


    @RequestMapping(value = "/employee/login", method = RequestMethod.POST)
    public ResponseEntity<Map> login(@RequestBody final User credentials) throws IOException {

        Map result;

        logger.info("********************************************************************************");

        if ((result = employeeService.validateUser(credentials)) != null) {

            String jwt = JwtUtil.generateToken(result.get("role").toString() + "::" + result.get("username").toString());

            logger.debug(jwt);
            result.put("token", jwt);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } else

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }


}
