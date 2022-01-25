package com.dbc.controller;

import com.dbc.service.stakerChangeImagesNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class OnlineProfileController {

            @Autowired
            com.dbc.service.fulfillBondService fulfillBondService;

            @Autowired
            stakerChangeImagesNameService imagesNameService;

            @Autowired
            com.dbc.service.stakerChangeMachineInfoService stakerChangeMachineInfoService;

            @Autowired
            com.dbc.service.bondMachineService bondMachineService;

            @ResponseBody
            @RequestMapping("/fulfillBond")
            public List<String> ful(){
                return fulfillBondService.fulfill();
            }


            @ResponseBody
            @RequestMapping("/stakerChangeMachineInfo")
            public List<String> stakers(){
                return stakerChangeMachineInfoService.staker();
            }

            @ResponseBody
            @RequestMapping("/stakerChangeImagesName")
            public List<String> Image(){
                return imagesNameService.ImagesName();
            }

            @ResponseBody
            @RequestMapping("/bondMachine")
            public List<String> bondMAchine(){return bondMachineService.bond();}


}
