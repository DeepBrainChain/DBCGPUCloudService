package com.dbc.controller;

import com.dbc.service.bookOneService;
import org.springframework.beans.factory.annotation.Autowired;
import com.dbc.service.ReportOfflineService;
import com.dbc.service.addPubkey;
import com.dbc.service.ReportOnlineService;
import com.dbc.service.reporterAddErrorInfoService;
import com.dbc.service.submitConfirmHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MaintainCommitteeContorller {

            @Autowired
            com.dbc.service.reportMachineService reportMachineService;
            @Autowired
            bookOneService service;
            @Autowired
            ReportOfflineService reportOfflineService;
            @Autowired
            ReportOnlineService onlineService;
            @Autowired
            addPubkey pubkey;
            @Autowired
            reporterAddErrorInfoService addErrorInfoService;
            @Autowired
            submitConfirmHash confirmHash;



            @ResponseBody
            @RequestMapping("/reportMachineState")
            public List<String> reportM(){
                return reportMachineService.reportMachine();
            }

            @ResponseBody
            @RequestMapping("/bookOne")
            public List<String> bookONe (){
                return service.book();
            }

            @ResponseBody
            @RequestMapping("/stakerReportOffline")
            public List<String> offline(){return reportOfflineService.ReportOff(); }

            @ResponseBody
            @RequestMapping("/stakerReportOnline")
            public List<String> Online(){return onlineService.Online(); }

            @ResponseBody
            @RequestMapping("/committeeAddPubkey")
            public List<String> key(){return pubkey.Pubkey();}

            @ResponseBody
            @RequestMapping("/reporterAddErrorInfo")
            public List<String> errorInfo(){return addErrorInfoService.AddErrorInfo();}

            @ResponseBody
            @RequestMapping("/submitConfirmHash")
            public List<String> confir(){return confirmHash.ConfirmHash();}



}
