package com.dbc.controller;

import com.dbc.service.AddConfirmHashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LeaseCommitteeController {

            @Autowired
            AddConfirmHashService leaseCommitteeService;

            @ResponseBody
            @RequestMapping("/addConfirmHash")
            public List<String> lease(){
                return leaseCommitteeService.leasecomm();
            }
}
