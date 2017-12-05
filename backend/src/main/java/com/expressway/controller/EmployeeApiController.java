package com.expressway.controller;

import com.expressway.jwt.JwtUtil;
import com.expressway.model.Employee;
import com.expressway.model.User;
import com.expressway.service.impl.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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

        if ((result = employeeService.validateEmployee(credentials)) != null) {

            String jwt = JwtUtil.generateToken(result.get("role").toString() + "::" + credentials.getUsername());

            result.put("token", jwt);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } else

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }


    @RequestMapping(value = "/employee/signup", method = RequestMethod.POST)
    public ResponseEntity<Boolean> signup(@RequestBody final Employee form) throws IOException {

        if (employeeService.addEmployee(form))
            return new ResponseEntity<>(true, HttpStatus.OK);

        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/employee/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") int userId) {

        if (employeeService.deleteEmployee(userId))
            return new ResponseEntity<>(true, HttpStatus.OK);

        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/get-employee", method = RequestMethod.GET)
    public ResponseEntity<List> getEmployees() {

        List result;
        if ((result = employeeService.getAllEmployee()) != null)
            return new ResponseEntity<>(result, HttpStatus.OK);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateEmployee(@RequestBody Employee form, @PathVariable("id") int userId) {

        if (employeeService.updateEmployee(form, userId))
            return new ResponseEntity<>(true, HttpStatus.OK);

        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/employee/customer/email-list", method = RequestMethod.GET)
    public ResponseEntity<List> getCustomerEmails() {
        List emails = employeeService.getCustomerEmails();

        if (emails != null)
            return new ResponseEntity<>(emails, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/employee/customer/flight-suggestions", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> getFlightSuggestions(@RequestParam("customerId") int customerId) {

        List<Map<String, Object>> suggestions = employeeService.getFlightSuggestions(customerId);

        if (suggestions != null)
            return new ResponseEntity<>(suggestions, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

}
